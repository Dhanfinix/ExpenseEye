package com.dhandev.expenseeye.data.model

data class TransactionGroupModel (
    val date: String,
    val transactionItem: List<TransactionItemModel>
)