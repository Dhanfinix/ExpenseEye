package com.dhandev.expenseeye.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dhandev.expenseeye.data.model.TransactionItemModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * from transaction_table ORDER BY dateInMillis DESC")
    fun getAllTransaction(): Flow<List<TransactionItemModel>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: TransactionItemModel)
}