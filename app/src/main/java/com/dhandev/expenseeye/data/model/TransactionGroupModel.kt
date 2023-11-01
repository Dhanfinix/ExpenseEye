package com.dhandev.expenseeye.data.model

data class TransactionGroupModel (
    val id : Int,
    val dateInMillis: Long,
    val transactionItem: List<TransactionItemModel>
)