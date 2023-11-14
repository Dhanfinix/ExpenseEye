package com.dhandev.expenseeye.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dhandev.expenseeye.data.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * from transaction_table ORDER BY id DESC")
    fun getAllTransaction(): Flow<List<Transaction>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Transaction)
}