package com.sergiupuhalschi.presentation.ui.common.views.indicators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sergiupuhalschi.presentation.ui.common.theme.LightGray

@Composable
fun HorizontalSeparatorLine(
    modifier: Modifier = Modifier,
    color: Color = LightGray
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color)
    )
}

@Composable
fun VerticalSeparatorLine(
    modifier: Modifier = Modifier,
    color: Color = LightGray
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(color)
    )
}