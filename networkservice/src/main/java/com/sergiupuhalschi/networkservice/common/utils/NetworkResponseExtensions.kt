package com.sergiupuhalschi.networkservice.common.utils

import com.sergiupuhalschi.common.models.ApiException
import com.sergiupuhalschi.common.models.NetworkConnectionException
import com.sergiupuhalschi.networkservice.common.models.NetworkResponse

suspend fun <T : Any, E : Any> NetworkResponse<T, E>.process(
    onApiError: suspend (E) -> T = { throw ApiException(message = "Unexpected error") }
): T {
    return when (this) {
        is NetworkResponse.ApiError -> onApiError(body)
        is NetworkResponse.NetworkError -> throw NetworkConnectionException(message = error.message)
        is NetworkResponse.Success -> body ?: throw IllegalStateException("Unable to obtain the response body")
        is NetworkResponse.UnknownError -> error?.let { throw it } ?: throw ApiException(
            message = "Unknown error"
        )
    }
}