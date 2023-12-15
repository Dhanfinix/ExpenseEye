package com.dhandev.rekapin.data.model

data class ProfileModel(
    var userName: String,
    var balance: Long,
    val budget: Long,
    val target: Long,
    val reportPeriod: Int
)
