package com.sergiupuhalschi.presentation.ui.common.views.buttons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.sergiupuhalschi.presentation.ui.common.theme.models.ButtonColors

object ComposeButtonsUtils {

    @Composable
    fun getBackgroundColorByState(
        isPressed: Boolean,
        enabled: Boolean,
        buttonStyle: ButtonColors.Colors
    ): Color {
        val backgroundColorByState by remember(isPressed, enabled, buttonStyle) {
            mutableStateOf(if (isPressed) buttonStyle.pressedBackgroundColor else if (!enabled) buttonStyle.disabledBackgroundColor else buttonStyle.initialBackgroundColor)
        }
        return backgroundColorByState
    }

    @Composable
    fun getContentColorByState(
        isPressed: Boolean,
        enabled: Boolean,
        buttonStyle: ButtonColors.Colors
    ): Color {
        val contentColorByState by remember(isPressed, enabled, buttonStyle) {
            mutableStateOf(if (isPressed) buttonStyle.pressedContentColor else if (!enabled) buttonStyle.disabledContentColor else buttonStyle.initialContentColor)
        }
        return contentColorByState
    }
}