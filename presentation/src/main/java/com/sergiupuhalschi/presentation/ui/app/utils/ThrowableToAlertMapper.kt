package com.sergiupuhalschi.presentation.ui.app.utils

import com.sergiupuhalschi.common.models.ApiException
import com.sergiupuhalschi.common.models.NetworkConnectionException
import com.sergiupuhalschi.common.utils.OneWayMapper
import com.sergiupuhalschi.presentation.R
import com.sergiupuhalschi.presentation.common.utils.ResourceProvider
import com.sergiupuhalschi.presentation.ui.common.models.AlertUI

class ThrowableToAlertMapper(
    private val resourceProvider: ResourceProvider
) : OneWayMapper<ThrowableToAlertMapper.Input, AlertUI> {

    data class Input(
        val throwable: Throwable,
        val overlay: Boolean
    )

    override fun from(input: Input): AlertUI {
        return AlertUI(
            title = resourceProvider.getString(R.string.General_Error_Title),
            message = getDisplayErrorMessage(input.throwable),
            overlay = input.overlay
        )
    }

    private fun getDisplayErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is NetworkConnectionException -> resourceProvider.getString(R.string.General_InternetError_Message)
            is ApiException -> throwable.message ?: resourceProvider.getString(R.string.General_Error_Message)
            else -> resourceProvider.getString(R.string.General_Error_Message)
        }
    }
}