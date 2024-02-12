package com.sergiupuhalschi.presentation.ui.app.models

import com.sergiupuhalschi.presentation.ui.common.navigation.NavigationRoute

sealed interface AppEventState {

    data object Idle : AppEventState

    data class Navigate(
        val route: NavigationRoute,
        val popUpRoute: NavigationRoute
    ) : AppEventState
}