package com.dhandev.rekapin.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dhandev.rekapin.data.model.TransactionItemModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * from transaction_table WHERE dateInMillis >= :fromDateInMillis ORDER BY dateInMillis DESC")
    fun getAllTransaction(fromDateInMillis: Long): Flow<List<TransactionItemModel>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: TransactionItemModel)
    @Query("SELECT SUM(total) FROM transaction_table WHERE isExpense = 1")
    fun getTotalExpenses(): Flow<Double>

    @Query("SELECT SUM(total) FROM transaction_table WHERE isExpense = 0")
    fun getTotalIncome(): Flow<Double>
}