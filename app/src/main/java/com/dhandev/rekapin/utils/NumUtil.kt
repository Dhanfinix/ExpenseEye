package com.dhandev.rekapin.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

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

    fun Double.formatThousand(): String {
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        return formatter.format(this)
    }

    fun String.clearThousandFormat(): String {
        // Use the parse() method to convert the string to a number
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        val number = formatter.parse(this)
        // Use the toString() method to convert the number to a string
        return number.toString()
    }
    fun String.clearDot(): String {
        // Use the same logic as the clearThousandFormat() function
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        val number = formatter.parse(this)
        return number.toString()
    }
}