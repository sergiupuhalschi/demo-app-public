package com.sergiupuhalschi.common.utils

import java.text.DecimalFormat

private const val DECIMAL_FORMAT = "###,###,###,##0.00"
private const val NO_TRAILING_ZEROS_DECIMAL_FORMAT = "###,###,###,###.##"

interface NumberFormatUtils {

    fun format(value: Double, noTrailingZeros: Boolean = false): String

    fun format(value: Double): String
}

internal class NumberFormatUtilsImplementation : NumberFormatUtils {

    override fun format(value: Double, noTrailingZeros: Boolean): String {
        val format = if (noTrailingZeros || value % 1 == 0.0) NO_TRAILING_ZEROS_DECIMAL_FORMAT else DECIMAL_FORMAT
        return DecimalFormat(format).format(value)
    }

    override fun format(value: Double): String {
        return DecimalFormat(DECIMAL_FORMAT).format(value)
    }
}