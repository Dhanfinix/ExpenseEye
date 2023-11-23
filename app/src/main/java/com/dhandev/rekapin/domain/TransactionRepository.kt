package com.dhandev.rekapin.domain

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.sqlite.db.SimpleSQLiteQuery
import com.dhandev.rekapin.data.local.room.TransactionDatabase
import com.dhandev.rekapin.data.model.TransactionItemModel
import kotlinx.coroutines.flow.first

class TransactionRepository(private val database: TransactionDatabase): ITransactionRepository {
//    override fun getAllTransaction(fromDataInMillis: Long): LiveData<PagedList<TransactionItemModel>> {
//        val query = SimpleSQLiteQuery("SELECT * from transaction_table WHERE dateInMillis >= $fromDataInMillis ORDER BY dateInMillis DESC")
//        val data = database.trxDao().getAllTransaction(query)
//        val config = PagedList.Config.Builder()
//            .setEnablePlaceholders(true)
//            .setInitialLoadSizeHint(10)
//            .setPageSize(10)
//            .build()
//        return LivePagedListBuilder(data, config).build()
//    }
    override fun getAllTransaction(fromDataInMillis: Long) = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { database.trxDao().getAllTransaction(fromDataInMillis) }
    ).flow
    override suspend fun insertItem(item: TransactionItemModel) = database.trxDao().insert(item)
    override suspend fun getTotalExpense(fromDateInMillis: Long) = database.trxDao().getTotalExpenses(fromDateInMillis)
    override suspend fun getTotalIncome(fromDateInMillis: Long) = database.trxDao().getTotalIncome(fromDateInMillis)
    override suspend fun getTotalBalance(fromDateInMillis: Long) = database.trxDao().getTotalIncome(fromDateInMillis).first() - database.trxDao().getTotalExpenses(fromDateInMillis).first()
    override suspend fun delete(item: TransactionItemModel) = database.trxDao().delete(item)
    override suspend fun update(item: TransactionItemModel) = database.trxDao().updateTransaction(item)
}