package com.dhandev.expenseeye.utils

import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.data.model.BottomNavItem

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            name = R.string.home,
            icon = R.drawable.ic_home,
            route = "home"
        ),
        BottomNavItem(
            name = R.string.report,
            icon = R.drawable.ic_report,
            route = "report"
        ),
        BottomNavItem(
            name = R.string.settings,
            icon = R.drawable.ic_settings,
            route = "settings"
        ),
    )
}