package com.dhandev.expenseeye.domain

import com.dhandev.expenseeye.data.local.room.TransactionDatabase
import com.dhandev.expenseeye.data.model.Transaction

class TransactionRepository(private val database: TransactionDatabase): ITransactionRepository {
    override fun getAllTransaction() = database.trxDao().getAllTransaction()
    override suspend fun insertItem(item: Transaction) = database.trxDao().insert(item)
}