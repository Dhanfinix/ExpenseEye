package com.dhandev.rekapin.data.model

data class TransactionGroupModel (
    val date: String,
    val transactionItem: List<TransactionItemModel>
)