package com.sergiupuhalschi.presentation.ui.common.views.buttons

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sergiupuhalschi.presentation.ui.common.theme.styles.buttonDimens
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appColors
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appTypography
import com.sergiupuhalschi.presentation.ui.common.utils.ClickListenerUtils
import com.sergiupuhalschi.presentation.ui.common.utils.darken
import com.sergiupuhalschi.presentation.ui.common.utils.lighten
import com.sergiupuhalschi.presentation.ui.common.utils.noRippleClickable
import com.sergiupuhalschi.presentation.ui.common.views.indicators.LoadingIndicatorDots

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    @DrawableRes iconRes: Int? = null,
    enabled: Boolean = true,
    color: Color = MaterialTheme.appColors.secondary,
    style: TextStyle = MaterialTheme.appTypography.titleSmall,
    textAlign: TextAlign = TextAlign.Center,
    fontSize: TextUnit = 15.sp,
    isLoading: Boolean = false,
    loadingIndicatorSpacing: Dp = MaterialTheme.buttonDimens.small.loadingIndicatorSpacing,
    loadingIndicatorSize: Dp = MaterialTheme.buttonDimens.small.loadingIndicatorSize,
    loadingIndicatorColor: Color = MaterialTheme.appColors.tertiary,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val lastTimeClicked = remember { mutableLongStateOf(0L) }
    val interactionSource = remember { MutableInteractionSource() }

    val isPressed by interactionSource.collectIsPressedAsState()
    val contentColorByState = when {
        isPressed -> color.darken()
        !enabled -> color.lighten(0.5f)
        else -> color
    }

    val contentAlpha by animateFloatAsState(targetValue = if (isLoading) 0f else 1f, label = "")
    val loadingAlpha by animateFloatAsState(targetValue = if (isLoading) 1f else 0f, label = "")

    Box(
        modifier = modifier
            .padding(contentPadding)
            .noRippleClickable(enabled = enabled && !isLoading) { ClickListenerUtils.safeOnClick(lastTimeClicked, onClick) },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .graphicsLayer { alpha = contentAlpha },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            iconRes?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    colorFilter = ColorFilter.tint(color)
                )

                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = text,
                fontSize = fontSize,
                style = style,
                color = contentColorByState,
                textAlign = textAlign
            )
        }

        LoadingIndicatorDots(
            isAnimating = isLoading,
            modifier = Modifier.graphicsLayer { alpha = loadingAlpha },
            color = loadingIndicatorColor,
            indicatorSpacing = loadingIndicatorSpacing,
            indicatorSize = loadingIndicatorSize
        )
    }
}