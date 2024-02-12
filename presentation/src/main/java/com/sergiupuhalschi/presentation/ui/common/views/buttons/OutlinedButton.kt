package com.sergiupuhalschi.presentation.ui.common.views.buttons

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.sergiupuhalschi.presentation.ui.common.theme.styles.buttonDimens
import com.sergiupuhalschi.presentation.ui.common.theme.models.ButtonColors
import com.sergiupuhalschi.presentation.ui.common.theme.models.ButtonDimens
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appTypography
import com.sergiupuhalschi.presentation.ui.common.utils.ClickListenerUtils
import com.sergiupuhalschi.presentation.ui.common.views.buttons.ComposeButtonsUtils.getBackgroundColorByState
import com.sergiupuhalschi.presentation.ui.common.views.indicators.LoadingIndicatorDots
import com.sergiupuhalschi.presentation.ui.common.views.texts.AutoSizeText

@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    buttonStyle: ButtonColors.Colors,
    enabled: Boolean = true,
    @DrawableRes iconRes: Int? = null,
    buttonDimens: ButtonDimens.Dimens = MaterialTheme.buttonDimens.large,
    bgColor: Color = Color.Transparent,
    textColor: Color? = null,
    isLoading: Boolean = false
) {
    val lastTimeClicked = remember { mutableLongStateOf(0L) }
    val interactionSource = remember { MutableInteractionSource() }

    val isPressed by interactionSource.collectIsPressedAsState()
    val borderColorByState = getBackgroundColorByState(isPressed, enabled, buttonStyle)

    val buttonShape = RoundedCornerShape(buttonDimens.cornerRadius)

    val contentAlpha by animateFloatAsState(targetValue = if (isLoading) 0f else 1f, label = "")
    val loadingAlpha by animateFloatAsState(targetValue = if (isLoading) 1f else 0f, label = "")

    androidx.compose.material.OutlinedButton(
        onClick = {
            ClickListenerUtils.safeOnClick(lastTimeClicked, onClick)
        },
        modifier = modifier
            .height(buttonDimens.height)
            .padding(all = 0.dp),
        border = BorderStroke(2.dp, borderColorByState),
        shape = buttonShape,
        colors = androidx.compose.material.ButtonDefaults.outlinedButtonColors(
            backgroundColor = bgColor,
            contentColor = borderColorByState,
            disabledContentColor = buttonStyle.disabledBackgroundColor
        ),
        enabled = enabled,
        interactionSource = interactionSource,
        content = {
            Box(
                contentAlignment = Alignment.Center
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
                        iconRes?.let {
                            Image(
                                painter = painterResource(id = it),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(borderColorByState),
                                alignment = Alignment.Center
                            )

                            Spacer(modifier = Modifier.width(9.dp))
                        }

                        AutoSizeText(
                            text = text,
                            style = MaterialTheme.appTypography.titleMedium,
                            fontSize = buttonDimens.fontSize,
                            color = textColor ?: borderColorByState,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    )
}