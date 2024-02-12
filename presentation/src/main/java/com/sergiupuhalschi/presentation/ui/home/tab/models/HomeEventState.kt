package com.sergiupuhalschi.presentation.ui.home.tab.models

sealed interface HomeEventState {

    data object Idle : HomeEventState

    data object Refreshing : HomeEventState
}