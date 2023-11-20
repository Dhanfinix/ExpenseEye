package com.dhandev.rekapin.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object DateUtil {
    fun millisToDate(millis: Long): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = Date(millis)
        return sdf.format(date)
    }

    fun millisToOnlyDate(millis: Long): String {
        val sdf = SimpleDateFormat("dd", Locale.getDefault())
        val date = Date(millis)
        return sdf.format(date)
    }

    fun millisToDateForGroup(timeInMillis: Long): String {
        val sdf = SimpleDateFormat("EEEE, dd MMM", Locale.getDefault())
        return sdf.format(Date(timeInMillis))
    }

    val currentDate = {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"))
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.timeInMillis
    }
    val oneDayInMillis : Long = 24 * 60 * 60 * 1000

    // For today's transactions
    val fromDateInMillisToday = currentDate

    // For last 7 days' transactions
    val fromDateInMillis7Days = currentDate() - 7 * oneDayInMillis

    // For last 30 days' transactions
    val fromDateInMillis30Days = currentDate() - 30 * oneDayInMillis

    fun fromReportPeriodDate(periodDate: Int): Long{
        val calendar = Calendar.getInstance()

        // If today's date is past the target day of the month
        if (calendar.get(Calendar.DAY_OF_MONTH) > periodDate) {
            // Set the day of the month to the target day
            calendar.set(Calendar.DAY_OF_MONTH, periodDate)
        } else {
            // Move to the previous month and set the day of the month to the target day
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DAY_OF_MONTH, periodDate)
        }

        val targetDateInMillis = calendar.timeInMillis
        val currentDateInMillis = Calendar.getInstance().timeInMillis

        // Convert the difference from milliseconds to days
        val daysPassed = TimeUnit.DAYS.convert(currentDateInMillis - targetDateInMillis, TimeUnit.MILLISECONDS)

        return currentDate() - daysPassed * oneDayInMillis
    }
}