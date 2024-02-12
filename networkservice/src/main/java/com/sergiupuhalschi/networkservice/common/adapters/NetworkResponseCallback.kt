package com.sergiupuhalschi.networkservice.common.adapters

import com.sergiupuhalschi.networkservice.common.models.NetworkResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

internal class NetworkResponseCallback<S : Any, E : Any>(
    private val onResponse: (NetworkResponse<S, E>) -> Unit,
    private val errorConverter: Converter<ResponseBody, E>
) : Callback<S> {

    override fun onResponse(call: Call<S>, response: Response<S>) {
        val body = response.body()
        val code = response.code()
        val error = response.errorBody()

        if (response.isSuccessful) {
            if (body != null) {
                onResponse(NetworkResponse.Success(body))
            } else {
                // Response is successful but the body is null
                onResponse(NetworkResponse.Success(null))
            }
        } else {
            val errorBody = when {
                error == null -> null
                error.contentLength() == 0L -> null
                else -> try {
                    errorConverter.convert(error)
                } catch (ex: Exception) {
                    null
                }
            }
            if (errorBody != null) {
                onResponse(NetworkResponse.ApiError(errorBody, code))
            } else {
                onResponse(NetworkResponse.UnknownError(null))
            }
        }
    }

    override fun onFailure(call: Call<S>, throwable: Throwable) {
        val networkResponse = when (throwable) {
            is SocketTimeoutException -> NetworkResponse.UnknownError(throwable)
            is IOException -> NetworkResponse.NetworkError(throwable)
            else -> NetworkResponse.UnknownError(throwable)
        }
        onResponse(networkResponse)
    }
}