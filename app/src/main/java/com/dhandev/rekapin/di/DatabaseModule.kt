package com.dhandev.rekapin.di

import androidx.room.Room
import com.dhandev.rekapin.data.local.room.TransactionDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val DatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            TransactionDatabase::class.java,
            "transaction_database"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<TransactionDatabase>().trxDao() }
}