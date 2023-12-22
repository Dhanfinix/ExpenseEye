package com.dhandev.rekapin.data.model

data class ProfileModel(
    var userName: String,
    var balance: Double,
    val budget: Double,
    val target: Double,
    val reportPeriod: Int
)
