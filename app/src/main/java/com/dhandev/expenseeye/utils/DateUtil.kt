package com.dhandev.expenseeye.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {
    fun millisToDate(millis: Long): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        val date = Date(millis)
        return sdf.format(date)
    }

    fun millisToOnlyDate(millis: Long): String {
        val sdf = SimpleDateFormat("dd", Locale.ENGLISH)
        val date = Date(millis)
        return sdf.format(date)
    }
}