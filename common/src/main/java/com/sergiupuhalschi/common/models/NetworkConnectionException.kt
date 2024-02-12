package com.sergiupuhalschi.common.models

import java.io.IOException

class NetworkConnectionException(
    override val message: String? = null
) : IOException()