package com.sergiupuhalschi.presentation.ui.onboarding.container

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sergiupuhalschi.presentation.ui.common.navigation.NavigationRoute
import com.sergiupuhalschi.presentation.ui.common.utils.screenExitHorizontalTransition
import com.sergiupuhalschi.presentation.ui.common.utils.screenPopEnterHorizontalTransition
import com.sergiupuhalschi.presentation.ui.common.utils.screenPopExitHorizontalTransition
import com.sergiupuhalschi.presentation.ui.common.utils.slideInFromLeftToRight
import com.sergiupuhalschi.presentation.ui.common.utils.slideInFromRightToLeft
import com.sergiupuhalschi.presentation.ui.common.utils.slideOutFromLeftToRight
import com.sergiupuhalschi.presentation.ui.common.utils.slideOutFromRightToLeft
import com.sergiupuhalschi.presentation.ui.onboarding.welcome.WelcomeScreen

@Composable
fun OnboardingContainerScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Onboarding.Welcome.route,
        enterTransition = { slideInFromRightToLeft() },
        exitTransition = { slideOutFromRightToLeft() },
        popEnterTransition = { slideInFromLeftToRight() },
        popExitTransition = { slideOutFromLeftToRight() }
    ) {
        composable(
            route = NavigationRoute.Onboarding.Welcome.route,
            enterTransition = { slideInFromRightToLeft() },
            exitTransition = screenExitHorizontalTransition,
            popEnterTransition = screenPopEnterHorizontalTransition,
            popExitTransition = screenPopExitHorizontalTransition
        ) {
            WelcomeScreen()
        }
    }
}