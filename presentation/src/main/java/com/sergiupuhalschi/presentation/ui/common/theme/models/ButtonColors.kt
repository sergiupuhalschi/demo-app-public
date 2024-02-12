package com.sergiupuhalschi.presentation.ui.common.theme.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import com.sergiupuhalschi.presentation.ui.common.utils.darken
import com.sergiupuhalschi.presentation.ui.common.utils.lighten
import com.sergiupuhalschi.presentation.ui.common.theme.Dark
import com.sergiupuhalschi.presentation.ui.common.theme.Purple40
import com.sergiupuhalschi.presentation.ui.common.theme.White

data class ButtonColors(
    val primary: Colors,
    val secondary: Colors,
    val tertiary: Colors
) {

    data class Colors(
        val initialContentColor: Color,
        val disabledContentColor: Color,
        val pressedContentColor: Color,
        val initialBackgroundColor: Color,
        val disabledBackgroundColor: Color,
        val pressedBackgroundColor: Color
    )

    companion object {

        val DefaultPrimary = colors(
            background = Dark,
            content = White
        )

        val DefaultSecondary = colors(
            background = Purple40,
            content = White
        )

        fun colors(background: Color, content: Color): Colors {
            return Colors(
                initialBackgroundColor = background,
                initialContentColor = content,
                disabledBackgroundColor = background.lighten(0.8f),
                disabledContentColor = content.lighten(0.5f),
                pressedBackgroundColor = background.lighten(),
                pressedContentColor = content.darken()
            )
        }

        fun toTertiaryBackground(color: Color): Color {
            return color.copy(
                alpha = 0.1f
            ).compositeOver(AppColors.LightTheme.surface)
        }
    }
}