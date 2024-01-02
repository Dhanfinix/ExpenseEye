package com.dhandev.rekapin.presentation.create

import com.dhandev.rekapin.data.model.CategoryItem

data class TransactionState(
    var firstOpened : Boolean = true,
    var nominal :String = "",
    var trxName :String = "",
    var selectedCategory :CategoryItem? = null,
    var trxDate : Long = System.currentTimeMillis()
)
