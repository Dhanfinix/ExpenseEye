package com.dhandev.rekapin.presentation.ui.component.homeCard

import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState

data class HomeCardState(
    var balance: MutableDoubleState,
    var budgetBalance: MutableDoubleState,
    var budgetLeft: MutableDoubleState,
    var targetAchieved: MutableDoubleState,
    var target: MutableDoubleState,
    var isShowBalance: MutableState<Boolean>,
    var isShown:(Boolean) -> Unit
)
