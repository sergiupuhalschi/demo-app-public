package com.sergiupuhalschi.presentation.ui.common.models

data class AlertUI(
    val title: String,
    val message: String,
    val type: Type = Type.Error,
    val overlay: Boolean = false
) {

    sealed interface Type {

        object Success : Type

        object Error : Type

        object Warning : Type
    }
}
