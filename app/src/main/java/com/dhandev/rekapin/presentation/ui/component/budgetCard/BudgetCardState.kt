package com.dhandev.rekapin.presentation.ui.component.budgetCard

import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState

data class BudgetCardState(
    var budgetBalance: MutableDoubleState,
    var budgetLeft: MutableDoubleState,
    var isShowBalance: MutableState<Boolean>,
    var isShown: (Boolean) -> Unit
)
