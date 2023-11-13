package com.dhandev.expenseeye.utils

import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.data.model.BottomNavItem
import com.dhandev.expenseeye.data.model.LandingItem

object Constants {
    val LandingPageItems = listOf(
        LandingItem(
            lottieRaw = R.raw.anim_welcome,
            title =R.string.landing_welcome,
            subtitle = R.string.landing_welcome_subtitle
        ),
        LandingItem(
            lottieRaw = R.raw.anim_in_out_money,
            title =R.string.landing_note_all,
            subtitle = R.string.landing_note_all_subtitle
        ),
        LandingItem(
            lottieRaw = R.raw.anim_report,
            title =R.string.landing_know_all,
            subtitle = R.string.landing_know_all_subtitle
        ),
        LandingItem(
            lottieRaw = R.raw.anim_start,
            title =R.string.landing_start,
            subtitle = R.string.landing_start_subtitle
        )
    )
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

    val categoryOutcomeName = listOf("Makan & Minum", "Transportasi", "Donasi", "Hadiah")
}