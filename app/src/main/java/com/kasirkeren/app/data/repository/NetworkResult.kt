package com.kasirkeren.app.data.repository

import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

sealed class AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>()
    data class Error(val message: String) : AppResult<Nothing>()
}

suspend fun <T> safeApiCall(call: suspend () -> T): AppResult<T> {
    return try {
        AppResult.Success(call())
    } catch (error: HttpException) {
        val detail = runCatching {
            val body = error.response()?.errorBody()?.string().orEmpty()
            JSONObject(body).optString("detail").takeIf { it.isNotBlank() }
        }.getOrNull()

        AppResult.Error(detail ?: "Terjadi kesalahan (${error.code()})")
    } catch (_: IOException) {
        AppResult.Error("Koneksi bermasalah. Periksa jaringan Anda.")
    } catch (error: Exception) {
        AppResult.Error(error.message ?: "Terjadi kesalahan tidak terduga")
    }
}
