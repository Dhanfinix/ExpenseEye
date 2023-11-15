package com.dhandev.expenseeye.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val trxName: String,
    @ColumnInfo
    val dateInMillis: Long,
    @ColumnInfo
    val category: String,
    @ColumnInfo
    val amount: Double,
    @ColumnInfo
    val isOutcome: Boolean
)
