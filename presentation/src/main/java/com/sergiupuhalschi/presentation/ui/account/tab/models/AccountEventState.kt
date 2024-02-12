package com.sergiupuhalschi.presentation.ui.account.tab.models

sealed interface AccountEventState {

    data object Idle : AccountEventState

    data object SignOutConfirmation : AccountEventState
}