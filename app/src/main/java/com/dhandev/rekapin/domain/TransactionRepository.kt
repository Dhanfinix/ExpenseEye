package com.dhandev.rekapin.domain

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import androidx.sqlite.db.SimpleSQLiteQuery
import com.dhandev.rekapin.data.local.room.TransactionDatabase
import com.dhandev.rekapin.data.model.TransactionGroupModel
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.utils.DateUtil.millisToDateForGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TransactionRepository(private val database: TransactionDatabase): ITransactionRepository {
    override fun getAllTransaction(fromDataInMillis: Long): Flow<PagingData<TransactionGroupModel>> {
        val pager = Pager(PagingConfig(pageSize = 20)) {
            database.trxDao().getAllTransaction(fromDataInMillis)
        }
        return pager.flow
            .map { pagingData -> pagingData.map { TransactionGroupModel(millisToDateForGroup(it.dateInMillis), listOf(it)) } }
            .map { it.insertSeparators { before, after ->
                if (after == null) {
                    // we're at the end of the list
                    return@insertSeparators null
                }

                if (before == null || before.date != after.date) {
                    // the dates are different, so we add a separator
                    return@insertSeparators TransactionGroupModel(after.date, emptyList())
                } else {
                    // no separator
                    null
                }
            } }
    }

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
//    override fun getAllTransaction(fromDataInMillis: Long) = Pager(
//        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
//        pagingSourceFactory = { database.trxDao().getAllTransaction(fromDataInMillis) }
//    ).flow
    override suspend fun insertItem(item: TransactionItemModel) = database.trxDao().insert(item)
    override suspend fun getTotalExpense(fromDateInMillis: Long) = database.trxDao().getTotalExpenses(fromDateInMillis)
    override suspend fun getTotalIncome(fromDateInMillis: Long) = database.trxDao().getTotalIncome(fromDateInMillis)
    override suspend fun getTotalBalance(fromDateInMillis: Long) = database.trxDao().getTotalIncome(fromDateInMillis).first() - database.trxDao().getTotalExpenses(fromDateInMillis).first()
    override suspend fun delete(item: TransactionItemModel) = database.trxDao().delete(item)
    override suspend fun update(item: TransactionItemModel) = database.trxDao().updateTransaction(item)
}