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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sergiupuhalschi.presentation.ui.common.theme.styles.buttonDimens
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appSpacing
import com.sergiupuhalschi.presentation.ui.common.theme.models.ButtonColors
import com.sergiupuhalschi.presentation.ui.common.theme.models.ButtonDimens
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appTypography
import com.sergiupuhalschi.presentation.ui.common.theme.styles.buttonColors
import com.sergiupuhalschi.presentation.ui.common.utils.ClickListenerUtils.safeOnClick
import com.sergiupuhalschi.presentation.ui.common.views.buttons.ComposeButtonsUtils.getBackgroundColorByState
import com.sergiupuhalschi.presentation.ui.common.views.buttons.ComposeButtonsUtils.getContentColorByState
import com.sergiupuhalschi.presentation.ui.common.views.indicators.LoadingIndicatorDots
import com.sergiupuhalschi.presentation.ui.common.views.texts.AutoSizeText

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    buttonStyle: ButtonColors.Colors = MaterialTheme.buttonColors.secondary,
    enabled: Boolean = true,
    @DrawableRes iconRes: Int? = null,
    isLoading: Boolean = false,
    buttonDimens: ButtonDimens.Dimens = MaterialTheme.buttonDimens.large,
    padding: PaddingValues = PaddingValues(2.dp),
    titleStyle: TextStyle = MaterialTheme.appTypography.titleMedium
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        text = text,
        buttonStyle = buttonStyle,
        enabled = enabled,
        leadingIcon = iconRes?.let {
            {
                Row {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        alignment = Alignment.Center,
                        colorFilter = ColorFilter.tint(buttonStyle.initialContentColor)
                    )

                    Spacer(modifier = Modifier.width(9.dp))
                }
            }
        },
        isLoading = isLoading,
        buttonDimens = buttonDimens,
        padding = padding,
        titleStyle = titleStyle
    )
}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    buttonStyle: ButtonColors.Colors,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    isLoading: Boolean = false,
    buttonDimens: ButtonDimens.Dimens = MaterialTheme.buttonDimens.large,
    padding: PaddingValues = PaddingValues(0.dp),
    titleStyle: TextStyle = MaterialTheme.appTypography.titleMedium
) {
    val contentAlpha by animateFloatAsState(targetValue = if (isLoading) 0f else 1f, label = "")
    val loadingAlpha by animateFloatAsState(targetValue = if (isLoading) 1f else 0f, label = "")
    val lastTimeClicked = remember { mutableLongStateOf(0L) }
    val interactionSource = remember { MutableInteractionSource() }

    val isPressed by interactionSource.collectIsPressedAsState()
    val contentColorByState = getContentColorByState(isPressed, enabled, buttonStyle)
    val backgroundColorByState = getBackgroundColorByState(isPressed, enabled, buttonStyle)
    val buttonShape = RoundedCornerShape(buttonDimens.cornerRadius)

    androidx.compose.material3.TextButton(
        onClick = {
            safeOnClick(lastTimeClicked, onClick)
        },
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
        enabled = enabled && !isLoading,
        contentPadding = padding
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            LoadingIndicatorDots(
                isAnimating = isLoading,
                modifier = Modifier.graphicsLayer { alpha = loadingAlpha },
                color = buttonStyle.disabledContentColor,
                indicatorSpacing = buttonDimens.loadingIndicatorSpacing,
                indicatorSize = buttonDimens.loadingIndicatorSize
            )

            Box(
                modifier = Modifier.graphicsLayer { alpha = contentAlpha }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    leadingIcon?.invoke()

                    AutoSizeText(
                        modifier = Modifier.padding(horizontal = MaterialTheme.appSpacing.VerySmallPadding),
                        text = text,
                        style = titleStyle,
                        color = contentColorByState,
                        fontSize = buttonDimens.fontSize,
                        maxLines = 2,
                        textAlign = TextAlign.Center
                    )

                    trailingIcon?.invoke()
                }
            }
        }
    }
}