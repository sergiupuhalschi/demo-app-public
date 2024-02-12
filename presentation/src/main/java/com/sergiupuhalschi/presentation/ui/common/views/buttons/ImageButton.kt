package com.sergiupuhalschi.presentation.ui.common.views.buttons

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sergiupuhalschi.presentation.ui.common.theme.styles.buttonDimens
import com.sergiupuhalschi.presentation.ui.common.theme.models.ButtonColors
import com.sergiupuhalschi.presentation.ui.common.theme.models.ButtonDimens
import com.sergiupuhalschi.presentation.ui.common.utils.ClickListenerUtils
import com.sergiupuhalschi.presentation.ui.common.views.buttons.ComposeButtonsUtils.getBackgroundColorByState
import com.sergiupuhalschi.presentation.ui.common.views.buttons.ComposeButtonsUtils.getContentColorByState
import com.sergiupuhalschi.presentation.ui.common.views.indicators.LoadingIndicatorDots

@Composable
fun ImageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes iconRes: Int,
    buttonStyle: ButtonColors.Colors,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    buttonDimens: ButtonDimens.Dimens = MaterialTheme.buttonDimens.large
) {
    val lastTimeClicked = remember { mutableLongStateOf(0L) }
    val interactionSource = remember { MutableInteractionSource() }

    val isPressed by interactionSource.collectIsPressedAsState()
    val contentColorByState = getContentColorByState(isPressed, enabled, buttonStyle)
    val backgroundColorByState = getBackgroundColorByState(isPressed, enabled, buttonStyle)
    val buttonShape = RoundedCornerShape(buttonDimens.cornerRadius)

    val contentAlpha by animateFloatAsState(targetValue = if (isLoading) 0f else 1f, label = "")
    val loadingAlpha by animateFloatAsState(targetValue = if (isLoading) 1f else 0f, label = "")

    Button(
        modifier = modifier
            .height(buttonDimens.height)
            .padding(0.dp),
        shape = buttonShape,
        colors = ButtonDefaults.textButtonColors(
            containerColor = backgroundColorByState,
            disabledContainerColor = backgroundColorByState,
            contentColor = contentColorByState,
            disabledContentColor = buttonStyle.disabledContentColor
        ),
        interactionSource = interactionSource,
        enabled = enabled,
        onClick = { ClickListenerUtils.safeOnClick(lastTimeClicked, onClick) },
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                LoadingIndicatorDots(
                    isAnimating = isLoading,
                    modifier = Modifier.graphicsLayer { alpha = loadingAlpha },
                    color = buttonStyle.disabledContentColor,
                    indicatorSpacing = buttonDimens.loadingIndicatorSpacing,
                    indicatorSize = buttonDimens.loadingIndicatorSize
                )
            }

            Box(
                modifier = Modifier.graphicsLayer { alpha = contentAlpha }
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(contentColorByState)
                )
            }
        }
    }
}