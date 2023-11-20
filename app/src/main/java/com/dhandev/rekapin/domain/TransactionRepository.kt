package com.dhandev.rekapin.domain

import com.dhandev.rekapin.data.local.room.TransactionDatabase
import com.dhandev.rekapin.data.model.TransactionItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class TransactionRepository(private val database: TransactionDatabase): ITransactionRepository {
    override fun getAllTransaction(fromDataInMillis: Long) = database.trxDao().getAllTransaction(fromDataInMillis)
    override suspend fun insertItem(item: TransactionItemModel) = database.trxDao().insert(item)
    override suspend fun getTotalExpense() = database.trxDao().getTotalExpenses()
    override suspend fun getTotalIncome() = database.trxDao().getTotalIncome()
    override suspend fun getTotalBalance() = database.trxDao().getTotalIncome().first() - database.trxDao().getTotalExpenses().first()
}