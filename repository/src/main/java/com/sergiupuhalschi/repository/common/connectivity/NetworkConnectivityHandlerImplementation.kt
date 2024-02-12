package com.sergiupuhalschi.repository.common.connectivity

import com.sergiupuhalschi.common.utils.NetworkConnectivityHandler
import com.sergiupuhalschi.networkservice.common.connectivity.NetworkConnectivityService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

internal class NetworkConnectivityHandlerImplementation(
    private val networkConnectivityService: NetworkConnectivityService
) : NetworkConnectivityHandler {

    private val isInternetConnectedSubject = MutableSharedFlow<Boolean>(1)
    private var isInternetConnected: Boolean = true

    private var connectivityObserverJob: Job? = null

    override fun startConnectivityListening(coroutineScope: CoroutineScope) {
        if (connectivityObserverJob != null) {
            Timber.e("Already observing internet connectivity. Ignoring..")
            return
        }

        connectivityObserverJob = coroutineScope.launch {
            observeInternetConnectivity()
        }
    }

    override fun stopConnectivityListening() {
        connectivityObserverJob?.cancel()
        connectivityObserverJob = null
    }

    override fun observeInternetConnection(): Flow<Boolean> {
        return isInternetConnectedSubject
    }

    override fun isInternetConnected(): Boolean {
        return isInternetConnected
    }

    private suspend fun observeInternetConnectivity() {
        networkConnectivityService.observeInternetConnectivity()
            .collect { isConnected ->
                isInternetConnectedSubject.emit(isConnected)
                isInternetConnected = isConnected
            }
    }
}