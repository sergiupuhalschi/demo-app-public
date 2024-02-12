package com.sergiupuhalschi.presentation.ui.common.views.indicators

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val MarginHalf = 5.dp
private const val NumIndicators = 3
private const val AnimationDurationMillis = 600
private const val AnimationDelayMillis = AnimationDurationMillis / NumIndicators

@Composable
fun LoadingIndicatorDots(
    isAnimating: Boolean,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    indicatorSpacing: Dp = MarginHalf,
    indicatorSize: Dp = 12.dp
) {
    val animatedValues = List(NumIndicators) { index ->
        var animatedValue by remember(Unit) { mutableFloatStateOf(0f) }
        LaunchedEffect(isAnimating) {
            if (isAnimating) {
                animate(
                    initialValue = 1f,
                    targetValue = 0.2f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = AnimationDurationMillis),
                        repeatMode = RepeatMode.Reverse,
                        initialStartOffset = StartOffset(AnimationDelayMillis * index),
                    ),
                ) { value, _ -> animatedValue = value }
            }
        }
        animatedValue
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        animatedValues.forEachIndexed { index, animatedValue ->
            val startPadding = if (index == 0) 0.dp else indicatorSpacing
            val endPadding = if (index == animatedValues.size - 1) 0.dp else indicatorSpacing
            LoadingDot(
                modifier = Modifier
                    .padding(
                        start = startPadding,
                        end = endPadding
                    )
                    .width(indicatorSize)
                    .aspectRatio(1f)
                    .graphicsLayer { alpha = animatedValue },
                color = color,
            )
        }
    }
}

@Composable
private fun LoadingDot(
    color: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = color)
    )
}