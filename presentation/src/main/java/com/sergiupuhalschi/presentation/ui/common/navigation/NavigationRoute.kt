package com.sergiupuhalschi.presentation.ui.common.navigation

sealed class NavigationRoute(
    val route: String
) {

    data object Common {

        val overlayScreenRoutes = listOf<String>()
    }

    data object Onboarding : NavigationRoute("onboardingGraph") {

        data object Welcome : NavigationRoute("welcome")
    }

    data object Main : NavigationRoute("mainGraph")

    data object Home : NavigationRoute("homeGraph") {

        data object Main : NavigationRoute("home")
    }

    data object Account : NavigationRoute("accountGraph") {

        data object Main : NavigationRoute("account")
    }
}