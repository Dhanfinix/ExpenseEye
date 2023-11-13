package com.dhandev.expenseeye.utils

import java.text.DecimalFormat

object NumUtil {
    fun formatDecimal(n: Double): String? {
        val formatter = DecimalFormat("#,###")
        val symbols = formatter.decimalFormatSymbols
        symbols.groupingSeparator = '.'
        symbols.decimalSeparator = ','
        formatter.decimalFormatSymbols = symbols
        return formatter.format(n)
    }

    fun formatDistance(d: Double) =
        if (d > 1000.0) {
            String.format("%s KM", formatDecimal(d/1000.0))
        } else {
            String.format("%s M", formatDecimal(d))
        }

    fun Long.formatThousand(): String {
        val decimalFormatter = DecimalFormat("#,###")
        return decimalFormatter.format(this)
    }

    fun String.clearThousandFormat(): String {
        return this.replace(",", "")
    }
}