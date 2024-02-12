package com.sergiupuhalschi.presentation.ui.common.views.indicators

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sergiupuhalschi.presentation.ui.common.theme.LightGray

fun Modifier.roundedShimmer(
    color: Color = LightGray,
    shapeRadius: Dp = 20.dp
): Modifier {
    return shimmer(
        isVisible = true,
        color = color,
        shape = RoundedCornerShape(shapeRadius)
    )
}

fun Modifier.shimmer(
    isVisible: Boolean = true,
    color: Color = LightGray,
    shape: Shape = RectangleShape
) = composed {
    if (!isVisible) {
        return@composed this
    }

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = ""
    )

    val brush = Brush.linearGradient(
        colors = color.toShimmerShades(),
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    background(
        brush = brush,
        shape = shape
    )
}

fun Color.toShimmerShades() = listOf(copy(0.9f), copy(0.2f), copy(0.9f))