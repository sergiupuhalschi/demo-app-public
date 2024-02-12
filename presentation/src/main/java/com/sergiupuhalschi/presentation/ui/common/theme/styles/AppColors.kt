package com.sergiupuhalschi.presentation.ui.common.theme.styles

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import com.sergiupuhalschi.presentation.ui.common.theme.models.AppColors
import com.sergiupuhalschi.presentation.ui.common.theme.models.ButtonColors

private var LocalColors = getLocalColors()
private var LocalButtonColors = getLocalButtonColors()

private fun getLocalColors(): ProvidableCompositionLocal<AppColors> {
    return compositionLocalOf { AppColors.LightTheme }
}

private fun getLocalButtonColors(): ProvidableCompositionLocal<ButtonColors> {
    return compositionLocalOf {
        AppColors.LightTheme.run {
            ButtonColors(
                primary = ButtonColors.colors(
                    background = primary,
                    content = onPrimary
                ),
                secondary = ButtonColors.colors(
                    background = secondary,
                    content = onPrimary
                ),
                tertiary = ButtonColors.colors(
                    background = ButtonColors.toTertiaryBackground(secondary),
                    content = secondary
                )
            )
        }
    }
}

val MaterialTheme.appColors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

val MaterialTheme.buttonColors: ButtonColors
    @Composable
    @ReadOnlyComposable
    get() = LocalButtonColors.current

fun AppColors.toColorScheme() = lightColorScheme(
    primary = primary,
    secondary = secondary,
    tertiary = tertiary,
    background = background,
    surface = surface,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onTertiary = onTertiary,
    onBackground = onBackground,
    onSurface = onSurface
)