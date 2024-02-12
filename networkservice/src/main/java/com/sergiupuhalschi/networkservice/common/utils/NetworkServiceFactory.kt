package com.sergiupuhalschi.networkservice.common.utils

import com.sergiupuhalschi.common.BuildConfig
import com.sergiupuhalschi.common.utils.NetworkConnectivityHandler
import com.sergiupuhalschi.networkservice.common.adapters.NetworkResponseAdapterFactory
import com.sergiupuhalschi.networkservice.common.interceptors.NetworkConnectivityRequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject

internal class NetworkServiceFactory @Inject constructor(
    private val okHttpClientBuilder: OkHttpClient.Builder,
    private val retrofitBuilder: Retrofit.Builder,
    private val networkConnectivityHandler: NetworkConnectivityHandler
) {

    inline fun <reified T> buildService(baseUrl: String): T {
        return retrofitBuilder.baseUrl(baseUrl)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(
                okHttpClientBuilder
                    .addInterceptor(NetworkConnectivityRequestInterceptor(networkConnectivityHandler))
                    .apply {
                        if (BuildConfig.DEBUG) {
                            addInterceptor(HttpLoggingInterceptor().apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            })
                        }
                    }
                    .build()
            )
            .build()
            .create(T::class.java)
    }
}