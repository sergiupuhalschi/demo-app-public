package com.sergiupuhalschi.presentation.ui.common.views.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergiupuhalschi.presentation.ui.common.theme.models.ButtonColors
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appColors
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appSpacing
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appTypography
import com.sergiupuhalschi.presentation.ui.common.theme.styles.buttonDimens
import com.sergiupuhalschi.presentation.ui.common.views.buttons.TextButton
import com.sergiupuhalschi.presentation.ui.common.views.dialogs.models.DialogButtonViewData
import com.sergiupuhalschi.presentation.ui.common.views.screens.FullScreen
import com.sergiupuhalschi.presentation.ui.common.views.utils.ScrimBackgroundLayout

@Composable
fun SimpleDialog(
    title: String,
    description: String,
    positiveButton: DialogButtonViewData?,
    dismissButton: DialogButtonViewData,
    positiveButtonColors: ButtonColors.Colors = ButtonColors.DefaultSecondary,
    negativeButtonColors: ButtonColors.Colors = ButtonColors.DefaultPrimary,
    cancelable: Boolean = true
) {
    val titleComposable: (@Composable () -> Unit)? = if (title.isBlank()) {
        null
    } else {
        {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.appTypography.titleMedium,
                color = MaterialTheme.appColors.primary,
                textAlign = TextAlign.Center
            )
        }
    }

    val descriptionComposable: (@Composable () -> Unit)? = if (description.isBlank()) {
        null
    } else {
        {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = description,
                style = MaterialTheme.appTypography.bodyLarge,
                color = MaterialTheme.appColors.primary,
                textAlign = TextAlign.Center
            )
        }
    }

    SimpleDialog(
        title = titleComposable,
        description = descriptionComposable,
        positiveButton = positiveButton,
        dismissButton = dismissButton,
        positiveButtonColors = positiveButtonColors,
        negativeButtonColors = negativeButtonColors,
        cancelable = cancelable
    )
}

@Composable
fun SimpleDialog(
    title: String,
    description: @Composable () -> Unit,
    positiveButton: DialogButtonViewData?,
    dismissButton: DialogButtonViewData,
    positiveButtonColors: ButtonColors.Colors = ButtonColors.DefaultSecondary,
    negativeButtonColors: ButtonColors.Colors = ButtonColors.DefaultPrimary,
    cancelable: Boolean = true
) {
    SimpleDialog(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.appTypography.titleMedium,
                color = MaterialTheme.appColors.primary,
                textAlign = TextAlign.Center
            )
        },
        description = description,
        positiveButton = positiveButton,
        dismissButton = dismissButton,
        positiveButtonColors = positiveButtonColors,
        negativeButtonColors = negativeButtonColors,
        cancelable = cancelable
    )
}

@Composable
fun SimpleDialog(
    title: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
    positiveButton: DialogButtonViewData?,
    dismissButton: DialogButtonViewData,
    positiveButtonColors: ButtonColors.Colors = ButtonColors.DefaultSecondary,
    negativeButtonColors: ButtonColors.Colors = ButtonColors.DefaultPrimary,
    cancelable: Boolean = true
) {
    FullScreen {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ScrimBackgroundLayout(
                isCancelable = cancelable,
                onDismiss = { dismissButton.onClick() }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .background(
                        color = MaterialTheme.appColors.surface,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(MaterialTheme.appSpacing.SmallPadding)
            ) {
                title?.let {
                    it()

                    Spacer(modifier = Modifier.height(MaterialTheme.appSpacing.SmallPadding))
                }

                description?.let {
                    it()

                    Spacer(modifier = Modifier.height(MaterialTheme.appSpacing.MediumPadding))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    positiveButton?.let {
                        TextButton(
                            modifier = Modifier.weight(1f),
                            onClick = it.onClick,
                            buttonStyle = positiveButtonColors,
                            buttonDimens = MaterialTheme.buttonDimens.medium,
                            text = it.title,
                            isLoading = it.isLoading,
                            enabled = it.isEnabled
                        )

                        Spacer(modifier = Modifier.width(MaterialTheme.appSpacing.MediumPadding))
                    }

                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = dismissButton.onClick,
                        buttonStyle = negativeButtonColors,
                        buttonDimens = MaterialTheme.buttonDimens.medium,
                        text = dismissButton.title,
                        padding = if (positiveButton == null) {
                            PaddingValues(horizontal = MaterialTheme.appSpacing.SmallPadding)
                        } else {
                            PaddingValues(0.dp)
                        },
                        isLoading = dismissButton.isLoading
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun SimpleDialogPreview() {
    SimpleDialog(
        title = "Terms & Policies",
        description = "No cancellation within 7 days",
        positiveButton = DialogButtonViewData(
            title = "OK",
            onClick = {}
        ),
        dismissButton = DialogButtonViewData(
            title = "Cancel",
            onClick = {}
        )
    )
}

@Composable
@Preview
private fun SimpleDialogNoPositiveButtonPreview() {
    SimpleDialog(
        title = "Terms & Policies",
        description = "No cancellation within 7 days",
        positiveButton = null,
        dismissButton = DialogButtonViewData(
            title = "Close",
            onClick = {}
        )
    )
}

@Composable
@Preview
private fun SimpleDialogNoTitlePreview() {
    SimpleDialog(
        title = "",
        description = "No cancellation within 7 days",
        positiveButton = DialogButtonViewData(
            title = "OK",
            onClick = {}
        ),
        dismissButton = DialogButtonViewData(
            title = "Close",
            onClick = {}
        )
    )
}

@Composable
@Preview
private fun SimpleDialogNoDescriptionPreview() {
    SimpleDialog(
        title = "Terms & Policies",
        description = "",
        positiveButton = DialogButtonViewData(
            title = "OK",
            onClick = {}
        ),
        dismissButton = DialogButtonViewData(
            title = "Close",
            onClick = {}
        )
    )
}