package com.sergiupuhalschi.presentation.ui.main.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.sergiupuhalschi.presentation.ui.main.models.TabScreen

interface TabNavigator {

    fun toTab(tabScreen: TabScreen)
}

class TabNavigatorImplementation(
    private val navController: NavHostController
) : TabNavigator {

    override fun toTab(tabScreen: TabScreen) {
        navController.navigate(tabScreen.graphRoute) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}