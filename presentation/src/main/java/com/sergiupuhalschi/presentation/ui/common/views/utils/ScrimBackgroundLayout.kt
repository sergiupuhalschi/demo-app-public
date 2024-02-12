package com.sergiupuhalschi.presentation.ui.common.views.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import com.sergiupuhalschi.presentation.ui.common.theme.ScrimBackgroundColor

@Composable
fun ScrimBackgroundLayout(
    modifier: Modifier = Modifier,
    color: Color = ScrimBackgroundColor,
    visibleFraction: Float = 1f,
    isCancelable: Boolean = false,
    onDismiss: (() -> Unit)? = null
) {
    Canvas(
        modifier
            .fillMaxSize()
            .then(
                if (isCancelable) {
                    Modifier
                        .pointerInput(onDismiss) { detectTapGestures { onDismiss?.invoke() } }
                        .semantics(mergeDescendants = true) {
                            onClick {
                                onDismiss?.invoke()
                                true
                            }
                        }
                } else {
                    Modifier
                }
            )
    ) {
        drawRect(
            color = color,
            alpha = visibleFraction
        )
    }
}