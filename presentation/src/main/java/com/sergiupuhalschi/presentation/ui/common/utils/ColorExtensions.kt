package com.sergiupuhalschi.presentation.ui.common.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

fun Color.darken(factor: Float = 0.4f): Color {
    return lerp(this, Color.Gray, factor)
}

fun Color.lighten(factor: Float = 0.3f): Color {
    return lerp(this, Color.LightGray, factor)
}