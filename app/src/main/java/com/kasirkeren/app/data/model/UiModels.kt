package com.kasirkeren.app.data.model

data class ItemUi(
    val id: Int,
    val name: String,
    val category: String,
    val sellPrice: Int,
    val buyPrice: Int,
    val stock: Int,
    val imageUrl: String? = null
)

data class CartLineUi(
    val item: ItemUi,
    val qty: Int
) {
    val subtotal: Int
        get() = item.sellPrice * qty
}

data class DashboardSummaryUi(
    val todayRevenue: Int,
    val totalTransactions: Int,
    val totalProfit: Int,
    val topItem: String,
    val weeklyRevenue: List<RevenuePointUi>
)

data class RevenuePointUi(
    val label: String,
    val amount: Int
)

data class TransactionItemUi(
    val name: String,
    val qty: Int,
    val unitPrice: Int
) {
    val subtotal: Int
        get() = qty * unitPrice
}

data class TransactionUi(
    val orderId: String,
    val status: String,
    val paymentType: String,
    val total: Int,
    val createdAt: String,
    val items: List<TransactionItemUi>
)

data class CsvImportSummaryUi(
    val totalRows: Int,
    val processed: Int,
    val created: Int,
    val updated: Int,
    val failed: Int,
    val firstErrors: List<String>
)

data class MidtransSessionUi(
    val orderId: String,
    val redirectUrl: String
)
