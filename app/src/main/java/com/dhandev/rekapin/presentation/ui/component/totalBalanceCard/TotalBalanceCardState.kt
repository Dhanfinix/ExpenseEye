package com.dhandev.rekapin.presentation.ui.component.totalBalanceCard

import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState

data class TotalBalanceCardState(
    var balance: MutableDoubleState,
    var targetAchieved: MutableDoubleState,
    var isShowBalance: MutableState<Boolean>,
    var target: MutableDoubleState,
    var isShown: (Boolean) -> Unit
)
