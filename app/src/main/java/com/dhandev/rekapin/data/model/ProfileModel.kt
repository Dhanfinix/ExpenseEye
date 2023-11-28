package com.dhandev.rekapin.data.model

data class ProfileModel(
    val userName: String,
    val balance: Long,
    val budget: Long,
    val target: Long,
    val reportPeriod: Int
)
