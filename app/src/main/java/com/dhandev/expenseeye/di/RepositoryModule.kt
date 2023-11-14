package com.dhandev.expenseeye.di

import com.dhandev.expenseeye.domain.ITransactionRepository
import com.dhandev.expenseeye.domain.TransactionRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single{ TransactionRepository(get()) }
}