package com.sergiupuhalschi.presentation.ui.common.utils

import android.os.SystemClock
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

const val CLICK_DEFAULT_INTERVAL = 300L

object ClickListenerUtils {

    fun safeOnClick(
        lastTimeClicked: MutableState<Long>,
        onClick: () -> Unit,
        clickInterval: Long = CLICK_DEFAULT_INTERVAL
    ) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked.value < clickInterval) {
            return
        }

        lastTimeClicked.value = SystemClock.elapsedRealtime()

        onClick()
    }
}

@Composable
fun Modifier.noRippleClickable(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    clickInterval: Long = CLICK_DEFAULT_INTERVAL,
    onClick: () -> Unit
): Modifier {
    val lastTimeClicked = remember { mutableLongStateOf(0) }

    return then(
        clickable(
            interactionSource,
            indication,
            enabled,
            onClickLabel,
            role,
            onClick = {
                ClickListenerUtils.safeOnClick(lastTimeClicked, onClick, clickInterval)
            }
        )
    )
}