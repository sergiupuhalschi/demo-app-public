package com.sergiupuhalschi.networkservice.common.interceptors

import com.sergiupuhalschi.common.models.NetworkConnectionException
import com.sergiupuhalschi.common.utils.NetworkConnectivityHandler
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class NetworkConnectivityRequestInterceptor(
    private val networkConnectivityHandler: NetworkConnectivityHandler
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkConnectivityHandler.isInternetConnected()) {
            Timber.d("No internet connection")
            throw NetworkConnectionException("Please check your internet connection or try again later.")
        }

        return chain.proceed(chain.request())
    }
}