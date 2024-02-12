package com.sergiupuhalschi.presentation.ui.account.common.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sergiupuhalschi.presentation.ui.account.tab.AccountScreen
import com.sergiupuhalschi.presentation.ui.common.navigation.NavigationRoute

fun NavGraphBuilder.accountGraph(
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition
) {
    navigation(
        startDestination = NavigationRoute.Account.Main.route,
        route = NavigationRoute.Account.route,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition
    ) {
        composable(
            route = NavigationRoute.Account.Main.route
        ) {
            AccountScreen()
        }
    }
}