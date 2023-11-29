package com.dhandev.rekapin.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SettingsModel(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val showSwitch: Boolean = true,
    val action: () -> Unit
)