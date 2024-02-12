package com.sergiupuhalschi.presentation.ui.main.models

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.sergiupuhalschi.presentation.R
import com.sergiupuhalschi.presentation.ui.common.navigation.NavigationRoute

sealed class TabScreen(
    val graphRoute: String,
    val startDestinationRoute: String,
    @StringRes val titleResId: Int,
    val icon: ImageVector,
    val title: String? = null
) {

    data object Home : TabScreen(
        graphRoute = NavigationRoute.Home.route,
        startDestinationRoute = NavigationRoute.Home.Main.route,
        titleResId = R.string.BottomTabs_HomeTitle,
        icon = Icons.Outlined.Home
    )

    data object Account : TabScreen(
        graphRoute = NavigationRoute.Account.route,
        startDestinationRoute = NavigationRoute.Account.Main.route,
        titleResId = R.string.BottomTabs_AccountTitle,
        icon = Icons.Outlined.AccountCircle
    )
}
