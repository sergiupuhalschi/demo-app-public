package com.sergiupuhalschi.presentation.ui.common.theme.styles

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import com.sergiupuhalschi.presentation.ui.common.theme.models.AppDimens
import com.sergiupuhalschi.presentation.ui.common.theme.models.ButtonDimens

private val LocalDimens = compositionLocalOf { AppDimens() }
private val LocalButtonDimens = compositionLocalOf { ButtonDimens() }

val MaterialTheme.appDimens: AppDimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current

val MaterialTheme.buttonDimens: ButtonDimens
    @Composable
    @ReadOnlyComposable
    get() = LocalButtonDimens.current