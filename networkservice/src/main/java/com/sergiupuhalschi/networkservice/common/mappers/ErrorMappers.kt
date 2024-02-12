package com.sergiupuhalschi.networkservice.common.mappers

import com.sergiupuhalschi.common.models.ApiException
import com.sergiupuhalschi.networkservice.common.models.ErrorDto

fun ErrorDto.toException(): ApiException {
    return ApiException(
        code = code ?: ErrorDto.UNKNOWN_ERROR_CODE,
        message = message
    )
}