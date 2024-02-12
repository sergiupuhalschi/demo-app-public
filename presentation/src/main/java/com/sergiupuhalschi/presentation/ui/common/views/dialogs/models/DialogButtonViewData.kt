package com.sergiupuhalschi.presentation.ui.common.views.dialogs.models

import androidx.compose.ui.graphics.Color

data class DialogButtonViewData(
    val title: String,
    val isLoading: Boolean = false,
    val isEnabled: Boolean = true,
    val color: Color? = null,
    val onClick: () -> Unit
)