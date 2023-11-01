package com.dhandev.expenseeye.data.model

data class TransactionItemModel(
    val id : Int,
    val title : String,
    val dateInMillis: Long,
    val total : Double,
    val category: String,
    val isExpense: Boolean
)
