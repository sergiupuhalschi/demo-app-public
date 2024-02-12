package com.sergiupuhalschi.presentation.ui.common.viewmodel.models

import com.sergiupuhalschi.presentation.ui.common.models.AlertUI

sealed class GeneralEventState {

    object Idle : GeneralEventState()

    object Loading : GeneralEventState()

    data class Error(val alertUI: AlertUI) : GeneralEventState()

    data class Success(val alertUI: AlertUI? = null) : GeneralEventState()
}
