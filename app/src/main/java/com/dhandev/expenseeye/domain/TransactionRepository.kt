package com.dhandev.expenseeye.domain

import com.dhandev.expenseeye.data.local.room.TransactionDatabase
import com.dhandev.expenseeye.data.model.TransactionItemModel

class TransactionRepository(private val database: TransactionDatabase): ITransactionRepository {
    override fun getAllTransaction(fromDataInMillis: Long) = database.trxDao().getAllTransaction(fromDataInMillis)
    override suspend fun insertItem(item: TransactionItemModel) = database.trxDao().insert(item)
}