package com.kasirkeren.app.data.di

import com.kasirkeren.app.data.api.RetrofitClient
import com.kasirkeren.app.data.repository.CheckoutRepository
import com.kasirkeren.app.data.repository.DashboardRepository
import com.kasirkeren.app.data.repository.ItemRepository
import com.kasirkeren.app.data.repository.TransactionRepository

object AppContainer {
    private val apiService by lazy { RetrofitClient.apiService }

    val itemRepository by lazy { ItemRepository(apiService) }
    val dashboardRepository by lazy { DashboardRepository(apiService) }
    val transactionRepository by lazy { TransactionRepository(apiService) }
    val checkoutRepository by lazy { CheckoutRepository(apiService) }
}
