package com.dhandev.rekapin.domain

import com.dhandev.rekapin.data.local.room.TransactionDatabase
import com.dhandev.rekapin.data.model.TransactionItemModel
import kotlinx.coroutines.flow.first

class TransactionRepository(private val database: TransactionDatabase): ITransactionRepository {
    override fun getAllTransaction(fromDataInMillis: Long) = database.trxDao().getAllTransaction(fromDataInMillis)
    override suspend fun insertItem(item: TransactionItemModel) = database.trxDao().insert(item)
    override suspend fun getTotalExpense(fromDateInMillis: Long) = database.trxDao().getTotalExpenses(fromDateInMillis)
    override suspend fun getTotalIncome(fromDateInMillis: Long) = database.trxDao().getTotalIncome(fromDateInMillis)
    override suspend fun getTotalBalance(fromDateInMillis: Long) = database.trxDao().getTotalIncome(fromDateInMillis).first() - database.trxDao().getTotalExpenses(fromDateInMillis).first()
    override suspend fun delete(item: TransactionItemModel) = database.trxDao().delete(item)
    override suspend fun update(item: TransactionItemModel) = database.trxDao().updateTransaction(item)
}