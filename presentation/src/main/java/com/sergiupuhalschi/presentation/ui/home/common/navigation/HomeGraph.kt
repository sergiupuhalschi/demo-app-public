package com.sergiupuhalschi.presentation.ui.home.common.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sergiupuhalschi.presentation.ui.common.navigation.NavigationRoute
import com.sergiupuhalschi.presentation.ui.home.tab.HomeScreen

fun NavGraphBuilder.homeGraph(
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = enterTransition,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = exitTransition
) {
    navigation(
        startDestination = NavigationRoute.Home.Main.route,
        route = NavigationRoute.Home.route,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition
    ) {
        composable(
            route = NavigationRoute.Home.Main.route
        ) {
            HomeScreen()
        }
    }
}