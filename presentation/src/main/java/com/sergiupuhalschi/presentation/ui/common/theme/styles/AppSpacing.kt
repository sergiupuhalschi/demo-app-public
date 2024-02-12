package com.sergiupuhalschi.presentation.ui.common.theme.styles

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergiupuhalschi.presentation.ui.common.theme.models.AppSpacing

private val LocalSpacing = compositionLocalOf { AppSpacing() }

val MaterialTheme.appSpacing: AppSpacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current