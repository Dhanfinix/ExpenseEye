package com.dhandev.expenseeye.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dhandev.expenseeye.data.model.Transaction

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun trxDao(): TransactionDao
}
