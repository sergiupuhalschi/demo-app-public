package com.sergiupuhalschi.common.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityHandler {

    fun startConnectivityListening(coroutineScope: CoroutineScope)

    fun stopConnectivityListening()

    fun observeInternetConnection(): Flow<Boolean>

    fun isInternetConnected(): Boolean
}