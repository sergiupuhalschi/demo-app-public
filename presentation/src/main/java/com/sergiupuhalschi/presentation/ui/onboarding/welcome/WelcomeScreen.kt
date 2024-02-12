package com.sergiupuhalschi.presentation.ui.onboarding.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.sergiupuhalschi.presentation.R
import com.sergiupuhalschi.presentation.ui.app.utils.SignInHandler
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appColors
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appSpacing
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appTypography
import com.sergiupuhalschi.presentation.ui.common.viewmodel.utils.lifecycleAwareHiltViewModel
import com.sergiupuhalschi.presentation.ui.common.views.buttons.TextButton
import com.sergiupuhalschi.presentation.ui.onboarding.welcome.models.WelcomeEventState

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel = lifecycleAwareHiltViewModel()
) {
    val eventState = viewModel.eventState().collectAsState().value

    WelcomeLayout(
        onSignInClick = viewModel::onSignInClick
    )

    when (eventState) {
        WelcomeEventState.SignIn -> SignInHandler(
            onSignInSuccess = viewModel::onSignInSuccess,
            onSignInFailed = viewModel::onSignInFailed,
            onDismiss = viewModel::acknowledgeEvent
        )
        WelcomeEventState.Idle -> {}
    }
}

@Composable
private fun WelcomeLayout(
    onSignInClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                color = MaterialTheme.appColors.surface
            )
            .padding(
                horizontal = MaterialTheme.appSpacing.MediumPadding,
                vertical = MaterialTheme.appSpacing.VeryLargePadding
            )
    ) {
        Spacer(modifier = Modifier.weight(2f))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.Welcome_Title, stringResource(id = R.string.app_name)),
            fontSize = 34.sp,
            color = MaterialTheme.appColors.onBackground,
            style = MaterialTheme.appTypography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(MaterialTheme.appSpacing.VeryLargePadding))

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.Welcome_NextBtn),
            onClick = onSignInClick
        )

        Spacer(modifier = Modifier.weight(3f))
    }
}

@Composable
@Preview
private fun WelcomePreview() {
    WelcomeLayout(
        onSignInClick = {}
    )
}