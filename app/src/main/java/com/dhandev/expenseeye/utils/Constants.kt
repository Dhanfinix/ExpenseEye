package com.dhandev.expenseeye.utils

import androidx.compose.ui.res.painterResource
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.data.model.BottomNavItem
import com.dhandev.expenseeye.data.model.CategoryItem
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

    val categoryOutcomeName = listOf(
        CategoryItem("Makan & Minum", R.drawable.ic_food_drinks),
        CategoryItem("Tagihan", R.drawable.ic_bill),
        CategoryItem("Donasi", R.drawable.ic_donate),
        CategoryItem("Edukasi", R.drawable.ic_education),
        CategoryItem("Olahraga", R.drawable.ic_exercise),
        CategoryItem("Kesehatan", R.drawable.ic_health),
        CategoryItem("Transportasi", R.drawable.ic_transportation),
    )

    val categoryIncomeName = listOf(
        CategoryItem("Hadiah", R.drawable.ic_gift),
        CategoryItem("Gaji", R.drawable.ic_payment),
    )
}