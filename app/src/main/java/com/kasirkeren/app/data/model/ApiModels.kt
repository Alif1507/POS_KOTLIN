package com.kasirkeren.app.data.model

import com.google.gson.annotations.SerializedName

data class ApiItem(
    val id: Int,
    val name: String,
    val price: Double,
    @SerializedName("purchase_price") val purchasePrice: Double,
    val stock: Int,
    val category: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("created_at") val createdAt: String
)

data class ApiItemCreateRequest(
    val name: String,
    val price: Double,
    @SerializedName("purchase_price") val purchasePrice: Double,
    val stock: Int,
    val category: String? = null,
    @SerializedName("image_url") val imageUrl: String? = null
)

data class ApiItemUpdateRequest(
    val name: String? = null,
    val price: Double? = null,
    @SerializedName("purchase_price") val purchasePrice: Double? = null,
    val stock: Int? = null,
    @SerializedName("is_active") val isActive: Boolean? = null,
    val category: String? = null,
    @SerializedName("image_url") val imageUrl: String? = null
)

data class ApiTransaction(
    val id: Int,
    @SerializedName("order_id") val orderId: String?,
    @SerializedName("total_amount") val totalAmount: Double?,
    val status: String?,
    @SerializedName("payment_type") val paymentType: String?,
    @SerializedName("transaction_items") val items: List<ApiTransactionItem>?,
    @SerializedName("created_at") val createdAt: String?
)

data class ApiTransactionItem(
    val id: Int,
    @SerializedName(value = "item_name", alternate = ["name"]) val itemName: String?,
    @SerializedName(value = "unit_price", alternate = ["price"]) val unitPrice: Double?,
    @SerializedName("purchase_price") val purchasePrice: Double?,
    val quantity: Int?
)

data class ApiTransactionCreateRequest(
    @SerializedName("total_amount") val totalAmount: Double,
    val items: List<ApiCartItemPayload>
)

data class ApiCartItemPayload(
    val id: Int,
    val name: String,
    val price: Double,
    @SerializedName("purchase_price") val purchasePrice: Double,
    val quantity: Int
)

data class ApiCreateTransactionResponse(
    @SerializedName("order_id") val orderId: String,
    @SerializedName("transaction_id") val transactionId: Int
)

data class ApiPaymentTokenRequest(
    @SerializedName("order_id") val orderId: String,
    @SerializedName("item_details") val itemDetails: List<ApiPaymentItemDetail>
)

data class ApiPaymentItemDetail(
    val id: String,
    val name: String,
    val price: Double,
    val quantity: Int
)

data class ApiPaymentTokenResponse(
    @SerializedName("snap_token") val snapToken: String,
    @SerializedName("redirect_url") val redirectUrl: String
)

data class ApiDashboardToday(
    @SerializedName("total_revenue") val totalRevenue: Double,
    @SerializedName("total_profit") val totalProfit: Double,
    @SerializedName("total_orders") val totalOrders: Int,
    @SerializedName("top_items") val topItems: List<ApiTopItem>
)

data class ApiTopItem(val name: String, val qty: Int)

data class ApiChartEntry(val date: String, val revenue: Double)

data class ApiCsvImportResponse(
    @SerializedName("total_rows") val totalRows: Int,
    @SerializedName("processed_rows") val processedRows: Int,
    @SerializedName("created_count") val createdCount: Int,
    @SerializedName("updated_count") val updatedCount: Int,
    @SerializedName("failed_count") val failedCount: Int,
    val errors: List<ApiCsvError>
)

data class ApiCsvError(val row: Int, val item: String, val reason: String)

data class ApiMessageResponse(val message: String)

data class ApiImageUrlResponse(@SerializedName("image_url") val imageUrl: String)

data class ApiHealthResponse(val message: String)
