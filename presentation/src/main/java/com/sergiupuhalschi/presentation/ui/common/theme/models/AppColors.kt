package com.sergiupuhalschi.presentation.ui.common.theme.models

import androidx.compose.ui.graphics.Color
import com.sergiupuhalschi.presentation.ui.common.theme.Black
import com.sergiupuhalschi.presentation.ui.common.theme.Dark
import com.sergiupuhalschi.presentation.ui.common.theme.Light
import com.sergiupuhalschi.presentation.ui.common.theme.LightGray
import com.sergiupuhalschi.presentation.ui.common.theme.Purple40
import com.sergiupuhalschi.presentation.ui.common.theme.PurpleGrey40

sealed class AppColors(
    val primary: Color = Dark,
    val secondary: Color = Purple40,
    val tertiary: Color = PurpleGrey40,
    val background: Color = LightGray,
    val surface: Color = Light,
    val onPrimary: Color = Light,
    val onSecondary: Color = Light,
    val onTertiary: Color = LightGray,
    val onBackground: Color = Dark,
    val onSurface: Color = Dark,
    val absolutePrimary: Color = Black
) {

    data object DarkTheme : AppColors()

    data object LightTheme : AppColors()
}