package com.sergiupuhalschi.presentation.ui.common.views.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sergiupuhalschi.presentation.ui.common.theme.styles.appDimens

@Composable
fun BottomTabScreen(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(bottom = MaterialTheme.appDimens.NavBarHeight)
    ) {
        content()
    }
}