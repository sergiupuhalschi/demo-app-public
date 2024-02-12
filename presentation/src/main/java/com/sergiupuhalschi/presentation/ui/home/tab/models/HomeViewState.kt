package com.sergiupuhalschi.presentation.ui.home.tab.models

sealed interface HomeViewState {

    data object Loading : HomeViewState

    data object Empty : HomeViewState

    data class Data(
        val items: List<PersonViewData>
    ) : HomeViewState
}