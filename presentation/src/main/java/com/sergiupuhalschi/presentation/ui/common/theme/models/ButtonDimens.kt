package com.sergiupuhalschi.presentation.ui.common.theme.models

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ButtonDimens(
    val large: Dimens = Dimens(
        height = 58.dp,
        cornerRadius = 20.dp,
        fontSize = 17.sp,
        loadingIndicatorSize = 12.dp,
        loadingIndicatorSpacing = 5.dp
    ),
    val medium: Dimens = Dimens(
        height = 44.dp,
        cornerRadius = 14.dp,
        fontSize = 15.sp,
        loadingIndicatorSize = 9.dp,
        loadingIndicatorSpacing = 4.dp
    ),
    val small: Dimens = Dimens(
        height = 34.dp,
        cornerRadius = 10.dp,
        fontSize = 13.sp,
        loadingIndicatorSize = 6.dp,
        loadingIndicatorSpacing = 3.dp
    )
) {

    data class Dimens(
        val height: Dp,
        val cornerRadius: Dp,
        val fontSize: TextUnit,
        val loadingIndicatorSize: Dp,
        val loadingIndicatorSpacing: Dp
    )
}