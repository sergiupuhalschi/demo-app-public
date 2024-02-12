package com.sergiupuhalschi.presentation.ui.account.tab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.sergiupuhalschi.presentation.R
import com.sergiupuhalschi.presentation.ui.account.tab.models.AccountEventState
import com.sergiupuhalschi.presentation.ui.account.tab.models.AccountViewState
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appColors
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appDimens
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appSpacing
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appTypography
import com.sergiupuhalschi.presentation.ui.common.utils.noRippleClickable
import com.sergiupuhalschi.presentation.ui.common.viewmodel.utils.lifecycleAwareHiltViewModel
import com.sergiupuhalschi.presentation.ui.common.views.dialogs.SimpleDialog
import com.sergiupuhalschi.presentation.ui.common.views.dialogs.models.DialogButtonViewData
import com.sergiupuhalschi.presentation.ui.common.views.images.NetworkImage
import com.sergiupuhalschi.presentation.ui.common.views.screens.BottomTabScreen
import com.sergiupuhalschi.presentation.ui.common.views.texts.AutoSizeText

@Composable
fun AccountScreen(viewModel: AccountViewModel = lifecycleAwareHiltViewModel()) {
    val viewState = viewModel.viewState().collectAsState().value
    val eventState = viewModel.eventState().collectAsState().value

    BottomTabScreen {
        AccountLayout(
            viewState = viewState,
            onSignOutClick = viewModel::onSignOutClick
        )
    }

    when (eventState) {
        AccountEventState.SignOutConfirmation -> SimpleDialog(
            title = stringResource(id = R.string.Account_SignOut_ConfirmationTitle),
            description = "",
            positiveButton = DialogButtonViewData(
                title = stringResource(id = R.string.Account_SignOut_ConfirmationPositiveBtn),
                onClick = viewModel::signOut
            ),
            dismissButton = DialogButtonViewData(
                title = stringResource(id = R.string.General_Cancel),
                onClick = viewModel::acknowledgeEvent
            )
        )
        AccountEventState.Idle -> {}
    }
}

@Composable
private fun AccountLayout(
    viewState: AccountViewState,
    onSignOutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(
                horizontal = MaterialTheme.appSpacing.MediumPadding,
                vertical = MaterialTheme.appSpacing.VeryLargePadding
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AutoSizeText(
                modifier = Modifier.weight(1f),
                text = viewState.name,
                color = MaterialTheme.appColors.primary,
                style = MaterialTheme.appTypography.headlineLarge,
                maxLines = 2
            )

            Spacer(modifier = Modifier.width(MaterialTheme.appSpacing.SmallPadding))

            NetworkImage(
                modifier = Modifier.size(MaterialTheme.appDimens.ProfilePictureSize),
                url = viewState.imageUrl,
                shape = CircleShape
            )
        }

        MenuItemLayout(
            title = stringResource(id = R.string.Account_SignOut_Btn),
            icon = Icons.AutoMirrored.Outlined.Logout,
            onClick = onSignOutClick
        )
    }
}

@Composable
fun MenuItemLayout(
    title: String,
    icon: ImageVector,
    bgColor: Color = MaterialTheme.appColors.primary,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.noRippleClickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = bgColor,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = icon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.appColors.surface)
            )
        }

        Spacer(modifier = Modifier.width(MaterialTheme.appSpacing.SmallPadding))

        Text(
            text = title,
            color = MaterialTheme.appColors.primary,
            style = MaterialTheme.appTypography.titleMedium
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun AccountPreview(
    @PreviewParameter(AccountPreviewProvider::class) viewState: AccountViewState
) {
    AccountLayout(
        viewState = viewState,
        onSignOutClick = {}
    )
}

class AccountPreviewProvider : PreviewParameterProvider<AccountViewState> {

    override val values: Sequence<AccountViewState>
        get() = sequenceOf(
            AccountViewState(
                name = "Test Account",
                imageUrl = "Test"
            ),
            AccountViewState()
        )
}