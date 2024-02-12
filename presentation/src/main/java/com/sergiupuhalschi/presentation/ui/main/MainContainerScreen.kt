package com.sergiupuhalschi.presentation.ui.main

import android.annotation.SuppressLint
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sergiupuhalschi.presentation.ui.account.common.navigation.accountGraph
import com.sergiupuhalschi.presentation.ui.common.navigation.NavigationRoute
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appColors
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appDimens
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appTypography
import com.sergiupuhalschi.presentation.ui.common.utils.screenEnterHorizontalTransition
import com.sergiupuhalschi.presentation.ui.common.utils.screenPopEnterHorizontalTransition
import com.sergiupuhalschi.presentation.ui.common.utils.screenPopExitHorizontalTransition
import com.sergiupuhalschi.presentation.ui.common.utils.slideInFromLeftToRight
import com.sergiupuhalschi.presentation.ui.common.utils.slideInFromRightToLeft
import com.sergiupuhalschi.presentation.ui.common.utils.slideOutFromLeftToRight
import com.sergiupuhalschi.presentation.ui.common.utils.slideOutFromRightToLeft
import com.sergiupuhalschi.presentation.ui.common.views.navigation.BottomNavigationItem
import com.sergiupuhalschi.presentation.ui.common.views.texts.AutoSizeText
import com.sergiupuhalschi.presentation.ui.home.common.navigation.homeGraph
import com.sergiupuhalschi.presentation.ui.main.models.TabScreen
import com.sergiupuhalschi.presentation.ui.main.navigation.TabNavigatorImplementation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainContainerScreen() {
    val navController = rememberNavController()
    val tabNavigator = remember(navController) { TabNavigatorImplementation(navController) }
    val navigationItems = remember {
        listOf(
            TabScreen.Home,
            TabScreen.Account
        )
    }
    val tabRoutes = remember(navigationItems) { navigationItems.map { listOf(it.graphRoute, it.startDestinationRoute) }.flatten() }

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                navigationItems = navigationItems,
                onTabClick = { tabNavigator.toTab(it) },
            )
        },
        containerColor = MaterialTheme.appColors.background,
        contentColor = MaterialTheme.appColors.background
    ) { _ ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoute.Home.route,
            enterTransition = { slideInFromRightToLeft() },
            exitTransition = { slideOutFromRightToLeft() },
            popEnterTransition = { slideInFromLeftToRight() },
            popExitTransition = { slideOutFromLeftToRight() }
        ) {
            homeGraph(
                enterTransition = {
                    if (initialState.destination.route in NavigationRoute.Common.overlayScreenRoutes) {
                        fadeIn()
                    } else {
                        slideInFromRightToLeft()
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        in tabRoutes -> slideOutFromRightToLeft()
                        in NavigationRoute.Common.overlayScreenRoutes -> fadeOut()
                        else -> slideOutFromRightToLeft()
                    }
                },
                popEnterTransition = {
                    if (initialState.destination.route in NavigationRoute.Common.overlayScreenRoutes) {
                        fadeIn()
                    } else {
                        slideInFromLeftToRight()
                    }
                },
                popExitTransition = {
                    if (targetState.destination.route in NavigationRoute.Common.overlayScreenRoutes) {
                        fadeOut()
                    } else {
                        slideOutFromLeftToRight()
                    }
                }
            )

            accountGraph(
                enterTransition = {
                    when (initialState.destination.route) {
                        in tabRoutes -> slideInFromRightToLeft()
                        in NavigationRoute.Common.overlayScreenRoutes -> fadeIn()
                        else -> slideInFromLeftToRight()
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        in tabRoutes -> slideOutFromLeftToRight()
                        in NavigationRoute.Common.overlayScreenRoutes -> fadeOut()
                        else -> slideOutFromRightToLeft()
                    }
                },
                popEnterTransition = screenPopEnterHorizontalTransition,
                popExitTransition = screenPopExitHorizontalTransition
            )
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    navigationItems: List<TabScreen>,
    onTabClick: (TabScreen) -> Unit
) {
    if (navigationItems.isEmpty()) {
        return
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isBottomBarDestination = currentDestination == null || navigationItems.any { it.startDestinationRoute == currentDestination.route }

    if (isBottomBarDestination) {
        BottomNavigation(
            modifier = Modifier
                .navigationBarsPadding()
                .height(MaterialTheme.appDimens.NavBarHeight),
            backgroundColor = MaterialTheme.appColors.surface,
            elevation = MaterialTheme.appDimens.NavBarElevation
        ) {
            navigationItems.forEach { tabScreen ->
                val selected = currentDestination?.hierarchy?.any { it.route == tabScreen.graphRoute } == true
                BottomNavigationItem(
                    selected = selected,
                    selectedContentColor = MaterialTheme.appColors.secondary,
                    unselectedContentColor = MaterialTheme.appColors.primary,
                    alwaysShowLabel = true,
                    icon = {
                        Icon(
                            imageVector = tabScreen.icon,
                            contentDescription = null
                        )
                    },
                    label = {
                        AutoSizeText(
                            text = tabScreen.title ?: stringResource(id = tabScreen.titleResId),
                            color = if (selected) MaterialTheme.appColors.secondary else MaterialTheme.appColors.primary,
                            fontSize = 11.sp,
                            style = MaterialTheme.appTypography.bodySmall
                        )
                    },
                    onClick = { onTabClick(tabScreen) }
                )
            }
        }
    }
}

@Composable
@Preview
private fun BottomBarPreview() {
    BottomBar(
        navController = rememberNavController(),
        navigationItems = listOf(
            TabScreen.Home,
            TabScreen.Account
        ),
        onTabClick = {}
    )
}