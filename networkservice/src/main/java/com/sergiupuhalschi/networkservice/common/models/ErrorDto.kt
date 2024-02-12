package com.sergiupuhalschi.networkservice.common.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorDto(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "message")
    val message: String?
) {

    companion object {

        const val UNKNOWN_ERROR_CODE = -1
    }
}