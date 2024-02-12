package com.sergiupuhalschi.common.utils

interface DateUtils {

    fun getCurrentTimeEpoch(): Long
}

internal class DateUtilsImplementation : DateUtils {

    override fun getCurrentTimeEpoch(): Long {
        return System.currentTimeMillis() / 1000
    }
}