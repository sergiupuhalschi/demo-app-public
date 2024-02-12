package com.sergiupuhalschi.common.models

open class ApiException(
    open val code: Int = -1,
    override val message: String?
) : Exception()