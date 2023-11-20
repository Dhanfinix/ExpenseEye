package com.dhandev.rekapin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class TransactionItemModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @ColumnInfo
    val title : String,
    @ColumnInfo
    val dateInMillis: Long,
    @ColumnInfo
    val total : Double,
    @ColumnInfo
    val category: String,
    @ColumnInfo
    val isExpense: Boolean
)
