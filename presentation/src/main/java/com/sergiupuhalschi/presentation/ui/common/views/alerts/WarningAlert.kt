package com.sergiupuhalschi.presentation.ui.common.views.alerts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sergiupuhalschi.presentation.ui.common.models.AlertUI
import com.sergiupuhalschi.presentation.ui.common.theme.Green
import com.sergiupuhalschi.presentation.ui.common.theme.Orange
import com.sergiupuhalschi.presentation.ui.common.theme.Red
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appColors
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appSpacing
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appTypography
import com.sergiupuhalschi.presentation.ui.common.views.screens.FullScreen
import com.sergiupuhalschi.presentation.ui.common.views.texts.AutoSizeText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val AUTO_DISMISS_MILLIS = 3000L
private val animationSpec: FiniteAnimationSpec<IntOffset> by lazy {
    spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessVeryLow
    )
}

@Composable
fun WarningAlert(
    modifier: Modifier = Modifier,
    ui: AlertUI,
    color: Color = when (ui.type) {
        AlertUI.Type.Error -> Red
        AlertUI.Type.Warning -> Orange
        else -> Green
    },
    autoDismiss: Boolean = true,
    onDismissed: (() -> Unit)? = null
) {
    val show = remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    if (autoDismiss) {
        LaunchedEffect(show.value) {
            if (show.value) {
                coroutineScope.launch {
                    delay(AUTO_DISMISS_MILLIS)
                    show.value = false
                    onDismissed?.invoke()
                }
            }
        }
    }

    if (ui.overlay) {
        FullScreen {
            AlertWrapper(
                modifier = modifier,
                ui = ui,
                show = show,
                color = color
            )
        }
    } else {
        AlertWrapper(
            modifier = modifier,
            ui = ui,
            show = show,
            color = color
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AlertWrapper(
    modifier: Modifier = Modifier,
    ui: AlertUI,
    show: MutableState<Boolean>,
    color: Color,
    onDismissed: (() -> Unit)? = null
) {
    AnimatedVisibility(
        visible = show.value,
        enter = slideInVertically(animationSpec) { -it },
        exit = slideOutVertically(animationSpec) { -it }
    ) {
        SwipeToDismiss(
            state = rememberDismissState(
                confirmStateChange = {
                    when (it) {
                        DismissValue.Default -> false
                        DismissValue.DismissedToEnd, DismissValue.DismissedToStart -> {
                            show.value = false
                            onDismissed?.invoke()
                            true
                        }
                    }
                }
            ),
            background = {},
            directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
            dismissThresholds = { FractionalThreshold(0.5f) }
        ) {
            ErrorAlertContent(
                modifier = modifier.statusBarsPadding(),
                ui = ui,
                color = color
            )
        }
    }
}

@Composable
private fun ErrorAlertContent(
    modifier: Modifier = Modifier,
    ui: AlertUI,
    color: Color
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 148.dp)
            .padding(
                top = 20.dp,
                start = MaterialTheme.appSpacing.MediumPadding,
                end = MaterialTheme.appSpacing.MediumPadding
            )
            .background(
                color = color,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(
                horizontal = MaterialTheme.appSpacing.MediumPadding,
                vertical = MaterialTheme.appSpacing.MediumPadding
            ),
        verticalArrangement = Arrangement.Center
    ) {
        if (ui.title.isNotBlank()) {
            AutoSizeText(
                text = ui.title,
                color = MaterialTheme.appColors.onSecondary,
                style = MaterialTheme.appTypography.titleSmall,
                maxLines = 2
            )
        }

        if (ui.message.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))

            AutoSizeText(
                text = ui.message,
                color = MaterialTheme.appColors.onSecondary,
                style = MaterialTheme.appTypography.bodySmall,
                maxLines = 3,
                minFontSize = 11.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ErrorAlertPreview() {
    WarningAlert(
        ui = AlertUI(
            title = "Oops",
            message = "Something went wrong"
        )
    )
}