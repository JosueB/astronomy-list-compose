package com.adyen.android.assignment.network

import retrofit2.HttpException
import retrofit2.Response

/**
 * Generic class to handle most of the responses from API
 */
interface BaseRepo {

    suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = call.invoke()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.Success(body)
            } else if (response.isSuccessful && Unit as? T != null) {
                Resource.Success(Unit as T)
            } else {
                Resource.Error(message = "Error Occurred during getting safe Api result, ${call.javaClass.name}, Response [$response]")
            }
        } catch (e: HttpException) {
            Resource.Error(message = "Error Occurred during getting safe Api result, ${call.javaClass.name}, ERROR [$e]")
        } catch (e: Throwable) {
            Resource.Error(message = "Error Occurred during getting safe Api result, ${call.javaClass.name}, ERROR [$e]")
        }
    }
}