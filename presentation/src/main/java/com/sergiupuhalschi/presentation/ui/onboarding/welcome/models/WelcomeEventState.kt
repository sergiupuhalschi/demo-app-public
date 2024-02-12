package com.sergiupuhalschi.presentation.ui.onboarding.welcome.models

sealed interface WelcomeEventState {

    data object Idle : WelcomeEventState

    data object SignIn : WelcomeEventState
}