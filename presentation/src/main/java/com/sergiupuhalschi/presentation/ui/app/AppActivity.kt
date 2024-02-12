package com.sergiupuhalschi.presentation.ui.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sergiupuhalschi.presentation.ui.app.models.AppEventState
import com.sergiupuhalschi.presentation.ui.app.utils.AppState
import com.sergiupuhalschi.presentation.ui.app.utils.AppStateHandler
import com.sergiupuhalschi.presentation.ui.main.MainContainerScreen
import com.sergiupuhalschi.presentation.ui.common.navigation.NavigationRoute
import com.sergiupuhalschi.presentation.ui.common.theme.AppTheme
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appColors
import com.sergiupuhalschi.presentation.ui.common.utils.screenEnterHorizontalTransition
import com.sergiupuhalschi.presentation.ui.common.utils.screenExitHorizontalTransition
import com.sergiupuhalschi.presentation.ui.common.utils.screenPopEnterHorizontalTransition
import com.sergiupuhalschi.presentation.ui.common.utils.screenPopExitHorizontalTransition
import com.sergiupuhalschi.presentation.ui.common.utils.slideInFromLeftToRight
import com.sergiupuhalschi.presentation.ui.common.utils.slideInFromRightToLeft
import com.sergiupuhalschi.presentation.ui.common.utils.slideOutFromLeftToRight
import com.sergiupuhalschi.presentation.ui.common.utils.slideOutFromRightToLeft
import com.sergiupuhalschi.presentation.ui.common.viewmodel.utils.lifecycleAwareViewModel
import com.sergiupuhalschi.presentation.ui.common.views.alerts.WarningAlert
import com.sergiupuhalschi.presentation.ui.onboarding.container.OnboardingContainerScreen
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: AppViewModel by lifecycleAwareViewModel()
    private val appStateHandler: AppStateHandler by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = MaterialTheme.appColors.surface
                ) {
                    NavigationContainer()

                    AppStateContainer()
                }
            }
        }
    }

    @Composable
    private fun AppStateContainer() {
        val coroutineScope = rememberCoroutineScope()

        when (val appState = appStateHandler.appState().collectAsState().value) {
            is AppState.Warning -> WarningAlert(
                ui = appState.alertUI
            ) {
                coroutineScope.launch { appStateHandler.acknowledgeState() }
            }
            is AppState.Idle -> {}
        }
    }

    @Composable
    private fun NavigationContainer() {
        val startDestination = viewModel.navigationStartDestination().collectAsState().value ?: return
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = startDestination.route
        ) {
            composable(
                route = NavigationRoute.Main.route,
                enterTransition = screenEnterHorizontalTransition,
                exitTransition = {
                    if (targetState.destination.hierarchy.any { it.route == NavigationRoute.Onboarding.route }) {
                        slideOutFromLeftToRight()
                    } else {
                        slideOutFromRightToLeft()
                    }
                },
                popEnterTransition = screenPopEnterHorizontalTransition,
                popExitTransition = screenPopExitHorizontalTransition
            ) {
                MainContainerScreen()
            }

            composable(
                route = NavigationRoute.Onboarding.route,
                enterTransition = {
                    if (initialState.destination.hierarchy.any { it.route == NavigationRoute.Main.route }) {
                        slideInFromLeftToRight()
                    } else {
                        slideInFromRightToLeft()
                    }
                },
                exitTransition = screenExitHorizontalTransition,
                popEnterTransition = screenPopEnterHorizontalTransition,
                popExitTransition = screenPopExitHorizontalTransition
            ) {
                OnboardingContainerScreen()
            }
        }

        EventStateHandler(navController)
    }

    @Composable
    private fun EventStateHandler(navController: NavController) {
        when (val eventState = viewModel.eventState().collectAsState().value) {
            is AppEventState.Navigate -> {
                val currentRoute = navController.currentBackStackEntry?.destination?.route

                if (currentRoute != eventState.route.route) {
                    navController.navigate(
                        route = eventState.route.route,
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(eventState.popUpRoute.route, true)
                            .build()
                    )
                }

                viewModel.acknowledgeEventState()
            }
            else -> {}
        }
    }
}