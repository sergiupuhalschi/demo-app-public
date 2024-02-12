package com.sergiupuhalschi.presentation.ui.app.utils

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.FirebaseUiException
import com.sergiupuhalschi.common.models.ApiException

@Composable
fun SignInHandler(
    onSignInSuccess: () -> Unit,
    onSignInFailed: (Throwable) -> Unit,
    onDismiss: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract(),
        onResult = {
            when (it.resultCode) {
                Activity.RESULT_OK -> onSignInSuccess()
                else -> {
                    val response = it.idpResponse

                    if (response == null) {
                        onDismiss()
                    } else {
                        onSignInFailed(response.error.toApiException())
                    }
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    listOf(
                        AuthUI.IdpConfig.GoogleBuilder()
                            .build()
                    )
                )
                .build()
        )
    }
}

private fun FirebaseUiException?.toApiException(): ApiException {
    return this?.run {
        ApiException(
            code = errorCode,
            message = message
        )
    } ?: ApiException(message = "An unexpected error occurred")
}