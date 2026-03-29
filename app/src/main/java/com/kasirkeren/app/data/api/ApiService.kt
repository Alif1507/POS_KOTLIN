package com.kasirkeren.app.data.api

import com.kasirkeren.app.data.model.ApiCreateTransactionResponse
import com.kasirkeren.app.data.model.ApiChartEntry
import com.kasirkeren.app.data.model.ApiCsvImportResponse
import com.kasirkeren.app.data.model.ApiDashboardToday
import com.kasirkeren.app.data.model.ApiHealthResponse
import com.kasirkeren.app.data.model.ApiImageUrlResponse
import com.kasirkeren.app.data.model.ApiItem
import com.kasirkeren.app.data.model.ApiItemCreateRequest
import com.kasirkeren.app.data.model.ApiItemUpdateRequest
import com.kasirkeren.app.data.model.ApiMessageResponse
import com.kasirkeren.app.data.model.ApiPaymentTokenRequest
import com.kasirkeren.app.data.model.ApiPaymentTokenResponse
import com.kasirkeren.app.data.model.ApiTransaction
import com.kasirkeren.app.data.model.ApiTransactionCreateRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    suspend fun health(): ApiHealthResponse

    @GET("api/items")
    suspend fun getItems(): List<ApiItem>

    @POST("api/items")
    suspend fun createItem(@Body body: ApiItemCreateRequest): ApiItem

    @PUT("api/items/{id}")
    suspend fun updateItem(
        @Path("id") id: Int,
        @Body body: ApiItemUpdateRequest
    ): ApiItem

    @DELETE("api/items/{id}")
    suspend fun deleteItem(@Path("id") id: Int): ApiMessageResponse

    @Multipart
    @POST("api/items/upload-image")
    suspend fun uploadImage(@Part file: MultipartBody.Part): ApiImageUrlResponse

    @Multipart
    @POST("api/items/import-csv")
    suspend fun importCsv(@Part file: MultipartBody.Part): ApiCsvImportResponse

    @GET("api/transactions")
    suspend fun getTransactions(): List<ApiTransaction>

    @POST("api/transactions")
    suspend fun createTransaction(
        @Body body: ApiTransactionCreateRequest
    ): ApiCreateTransactionResponse

    @POST("api/payment/create-token")
    suspend fun createPaymentToken(
        @Body body: ApiPaymentTokenRequest
    ): ApiPaymentTokenResponse

    @GET("api/dashboard/today")
    suspend fun getDashboardToday(): ApiDashboardToday

    @GET("api/dashboard/chart")
    suspend fun getDashboardChart(): List<ApiChartEntry>

    @POST("api/payment/success/{orderId}")
    suspend fun paymentSuccess(
        @Path("orderId") orderId: String,
        @Query("payment_type") paymentType: String = "Tunai"
    ): ApiMessageResponse
}
