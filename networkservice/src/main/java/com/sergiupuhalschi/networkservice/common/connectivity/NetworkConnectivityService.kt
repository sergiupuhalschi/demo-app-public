package com.sergiupuhalschi.networkservice.common.connectivity

import android.annotation.SuppressLint
import android.content.Context
import com.sergiupuhalschi.common.dispatchers.DispatcherProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import ru.beryukhov.reactivenetwork.ReactiveNetwork
import ru.beryukhov.reactivenetwork.internet.observing.InternetObservingSettings
import ru.beryukhov.reactivenetwork.internet.observing.error.ErrorHandler
import ru.beryukhov.reactivenetwork.internet.observing.strategy.WalledGardenInternetObservingStrategy
import timber.log.Timber
import java.net.HttpURLConnection

private const val DEFAULT_TIMEOUT = 5000
private const val DEFAULT_INTERVAL = 5000
private const val DEFAULT_INITIAL_INTERVAL = 0
private const val DEFAULT_PORT = 443
private const val DEFAULT_HOST = "https://clients3.google.com/generate_204"

interface NetworkConnectivityService {

    fun observeNetworkConnectivity(): Flow<Boolean>

    fun observeInternetConnectivity(): Flow<Boolean>

    suspend fun checkInternetConnectivity(): Boolean
}

internal class NetworkConnectivityServiceImplementation(
    private val context: Context,
    private val dispatcherProvider: DispatcherProvider
) : NetworkConnectivityService {

    private val reactiveNetwork by lazy { ReactiveNetwork() }
    private val internetObservingSettings by lazy {
        InternetObservingSettings.builder()
            .host(DEFAULT_HOST)
            .initialInterval(DEFAULT_INITIAL_INTERVAL)
            .interval(DEFAULT_INTERVAL)
            .port(DEFAULT_PORT)
            .timeout(DEFAULT_TIMEOUT)
            .httpResponse(HttpURLConnection.HTTP_NO_CONTENT)
            .errorHandler(
                object : ErrorHandler {
                    override fun handleError(exception: Exception?, message: String?) {
                        Timber.d("ReactiveNetwork message: $message")
                    }
                }
            )
            .strategy(WalledGardenInternetObservingStrategy())
            .build()
    }

    @SuppressLint("MissingPermission")
    override fun observeNetworkConnectivity(): Flow<Boolean> {
        return reactiveNetwork.observeNetworkConnectivity(context)
            .map { it.available }
            .flowOn(dispatcherProvider.io)
    }

    @SuppressLint("MissingPermission")
    override fun observeInternetConnectivity(): Flow<Boolean> {
        return reactiveNetwork.observeInternetConnectivity(internetObservingSettings)
            .flowOn(dispatcherProvider.io)
    }

    @SuppressLint("MissingPermission")
    override suspend fun checkInternetConnectivity(): Boolean {
        return withContext(dispatcherProvider.io) {
            reactiveNetwork.checkInternetConnectivity(internetObservingSettings)
        }
    }
}