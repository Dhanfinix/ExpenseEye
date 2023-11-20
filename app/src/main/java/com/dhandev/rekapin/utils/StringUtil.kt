package com.dhandev.rekapin.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

object StringUtil {
    fun formatRp (nominal: String): String {
        val formatter = NumberFormat.getNumberInstance(Locale("in", "ID")) as DecimalFormat
        val formattedNominal = formatter.format(nominal.toDouble())
        return "Rp $formattedNominal"
    }
    fun formatRpWithSign (nominal: String, isExpense: Boolean): String {
        val clearNominal = if (nominal[0] == '-') nominal.removePrefix("-") else nominal
        val sign = if (isExpense) "-" else "+"
        val formatter = NumberFormat.getNumberInstance(Locale("in", "ID")) as DecimalFormat
        val formattedNominal = formatter.format(clearNominal.toDouble())
        return "${sign}Rp $formattedNominal"
    }
}