package com.dhandev.rekapin.data.local.room

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.dhandev.rekapin.data.model.TransactionItemModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
//    @RawQuery(observedEntities = [TransactionItemModel::class])   query: SupportSQLiteQuery
    @Query("SELECT * from transaction_table WHERE dateInMillis >= :fromDateInMillis ORDER BY dateInMillis DESC")
    fun getAllTransaction(fromDateInMillis: Long): PagingSource<Int, TransactionItemModel>//DataSource.Factory<Int, TransactionItemModel>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: TransactionItemModel)
    @Delete
    suspend fun delete(item: TransactionItemModel)
    @Update
    suspend fun updateTransaction(item: TransactionItemModel)
    @Query("SELECT SUM(total) FROM transaction_table WHERE isExpense = 1 AND dateInMillis >= :fromDateInMillis")
    fun getTotalExpenses(fromDateInMillis: Long): Flow<Double>

    @Query("SELECT SUM(total) FROM transaction_table WHERE isExpense = 0  AND dateInMillis >= :fromDateInMillis")
    fun getTotalIncome(fromDateInMillis: Long): Flow<Double>
}