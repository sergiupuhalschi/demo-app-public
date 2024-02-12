package com.sergiupuhalschi.presentation.ui.app.utils

import com.sergiupuhalschi.presentation.ui.common.models.AlertUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed interface AppState {

    data object Idle : AppState

    data class Warning(
        val alertUI: AlertUI
    ) : AppState
}

class AppStateHandler(
    private val throwableToAlertMapper: ThrowableToAlertMapper
) {

    private val appState = MutableStateFlow<AppState>(AppState.Idle)

    fun appState(): StateFlow<AppState> = appState

    suspend fun handleError(throwable: Throwable, overlay: Boolean) {
        appState.emit(
            AppState.Warning(
                alertUI = throwableToAlertMapper.from(
                    input = ThrowableToAlertMapper.Input(
                        throwable = throwable,
                        overlay = overlay
                    )
                )
            )
        )
    }

    suspend fun acknowledgeState() {
        appState.emit(AppState.Idle)
    }
}