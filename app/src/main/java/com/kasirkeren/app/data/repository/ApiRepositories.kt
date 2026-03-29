package com.kasirkeren.app.data.repository

import com.kasirkeren.app.data.api.ApiService
import com.kasirkeren.app.data.model.ApiCartItemPayload
import com.kasirkeren.app.data.model.ApiItem
import com.kasirkeren.app.data.model.ApiItemCreateRequest
import com.kasirkeren.app.data.model.ApiItemUpdateRequest
import com.kasirkeren.app.data.model.ApiPaymentItemDetail
import com.kasirkeren.app.data.model.ApiPaymentTokenRequest
import com.kasirkeren.app.data.model.ApiTransaction
import com.kasirkeren.app.data.model.ApiTransactionCreateRequest
import com.kasirkeren.app.data.model.CartLineUi
import com.kasirkeren.app.data.model.CsvImportSummaryUi
import com.kasirkeren.app.data.model.DashboardSummaryUi
import com.kasirkeren.app.data.model.ItemUi
import com.kasirkeren.app.data.model.MidtransSessionUi
import com.kasirkeren.app.data.model.RevenuePointUi
import com.kasirkeren.app.data.model.TransactionItemUi
import com.kasirkeren.app.data.model.TransactionUi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

class ItemRepository(private val api: ApiService) {
    suspend fun getItems(): AppResult<List<ItemUi>> = safeApiCall {
        api.getItems().filter { it.isActive }.map { it.toUi() }
    }

    suspend fun saveItem(
        id: Int?,
        name: String,
        sku: String,
        sellPrice: Int,
        buyPrice: Int,
        stock: Int
    ): AppResult<ItemUi> = safeApiCall {
        val normalizedSku = sku.trim().ifBlank { null }
        if (id == null) {
            api.createItem(
                ApiItemCreateRequest(
                    name = name,
                    price = sellPrice.toDouble(),
                    purchasePrice = buyPrice.toDouble(),
                    stock = stock,
                    category = normalizedSku
                )
            ).toUi()
        } else {
            api.updateItem(
                id = id,
                body = ApiItemUpdateRequest(
                    name = name,
                    price = sellPrice.toDouble(),
                    purchasePrice = buyPrice.toDouble(),
                    stock = stock,
                    category = normalizedSku
                )
            ).toUi()
        }
    }

    suspend fun deleteItem(id: Int): AppResult<Unit> = safeApiCall {
        api.deleteItem(id)
        Unit
    }

    suspend fun importCsv(fileName: String, fileBytes: ByteArray): AppResult<CsvImportSummaryUi> = safeApiCall {
        val requestBody = fileBytes.toRequestBody("text/csv".toMediaType())
        val filePart = MultipartBody.Part.createFormData("file", fileName, requestBody)
        api.importCsv(filePart).let { response ->
            CsvImportSummaryUi(
                totalRows = response.totalRows,
                processed = response.processedRows,
                created = response.createdCount,
                updated = response.updatedCount,
                failed = response.failedCount,
                firstErrors = response.errors.map {
                    "Baris ${it.row} - ${it.item}: ${it.reason}"
                }
            )
        }
    }

    private fun ApiItem.toUi(): ItemUi = ItemUi(
        id = id,
        name = name,
        category = category.orEmpty().ifBlank { "Lainnya" },
        sellPrice = price.roundToInt(),
        buyPrice = purchasePrice.roundToInt(),
        stock = stock,
        imageUrl = imageUrl
    )
}

class DashboardRepository(private val api: ApiService) {
    suspend fun getDashboardSummary(): AppResult<DashboardSummaryUi> = safeApiCall {
        val today = api.getDashboardToday()
        val chart = api.getDashboardChart()

        DashboardSummaryUi(
            todayRevenue = today.totalRevenue.roundToInt(),
            totalTransactions = today.totalOrders,
            totalProfit = today.totalProfit.roundToInt(),
            topItem = today.topItems.maxByOrNull { it.qty }?.name ?: "-",
            weeklyRevenue = chart.map { point ->
                RevenuePointUi(
                    label = runCatching {
                        LocalDate.parse(point.date)
                            .format(DateTimeFormatter.ofPattern("EEE", Locale.forLanguageTag("id-ID")))
                    }.getOrDefault(point.date.takeLast(2)),
                    amount = point.revenue.roundToInt()
                )
            }
        )
    }
}

class TransactionRepository(private val api: ApiService) {
    suspend fun getTransactions(): AppResult<List<TransactionUi>> = safeApiCall {
        api.getTransactions().map { it.toUi() }
    }

    private fun ApiTransaction.toUi(): TransactionUi = TransactionUi(
        orderId = orderId ?: "-",
        status = status ?: "pending",
        paymentType = paymentType ?: "Tunai",
        total = (totalAmount ?: 0.0).roundToInt(),
        createdAt = createdAt ?: "1970-01-01T00:00:00",
        items = items.orEmpty().map { item ->
            TransactionItemUi(
                name = item.itemName?.takeIf { it.isNotBlank() } ?: "(Tanpa Nama)",
                qty = (item.quantity ?: 0).coerceAtLeast(0),
                unitPrice = (item.unitPrice ?: 0.0).roundToInt()
            )
        }
    )
}

class CheckoutRepository(private val api: ApiService) {
    private fun toTransactionPayload(cart: List<CartLineUi>): ApiTransactionCreateRequest {
        return ApiTransactionCreateRequest(
            totalAmount = cart.sumOf { it.subtotal }.toDouble(),
            items = cart.map { line ->
                ApiCartItemPayload(
                    id = line.item.id,
                    name = line.item.name,
                    price = line.item.sellPrice.toDouble(),
                    purchasePrice = line.item.buyPrice.toDouble(),
                    quantity = line.qty
                )
            }
        )
    }

    suspend fun checkoutCash(cart: List<CartLineUi>): AppResult<String> = safeApiCall {
        val response = api.createTransaction(toTransactionPayload(cart))
        api.paymentSuccess(orderId = response.orderId, paymentType = "Tunai")
        response.orderId
    }

    suspend fun checkoutMidtrans(cart: List<CartLineUi>): AppResult<MidtransSessionUi> = safeApiCall {
        val transaction = api.createTransaction(toTransactionPayload(cart))
        val token = api.createPaymentToken(
            ApiPaymentTokenRequest(
                orderId = transaction.orderId,
                itemDetails = cart.map { line ->
                    ApiPaymentItemDetail(
                        id = line.item.id.toString(),
                        name = line.item.name,
                        price = line.item.sellPrice.toDouble(),
                        quantity = line.qty
                    )
                }
            )
        )

        val url = token.redirectUrl.ifBlank {
            throw IllegalStateException("Redirect URL Midtrans tidak tersedia")
        }
        MidtransSessionUi(orderId = transaction.orderId, redirectUrl = url)
    }

    suspend fun confirmMidtrans(orderId: String): AppResult<Unit> = safeApiCall {
        api.paymentSuccess(orderId = orderId, paymentType = "Midtrans")
        Unit
    }
}
