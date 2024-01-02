package com.dhandev.rekapin.presentation.ui.component.profileBottomSheet

import com.dhandev.rekapin.data.model.ProfileModel

data class ProfileState(
    var userName : String= "",
    var balance : String= "",
    var budget : String= "",
    var target : String = "",
    var reportPeriod : Int = 14
){
    fun mapModelToState(data: ProfileModel){
        userName = data.userName
        balance = data.balance.toString()
        budget = data.budget.toString()
        target = data.target.toString()
        reportPeriod = data.reportPeriod
    }
}
