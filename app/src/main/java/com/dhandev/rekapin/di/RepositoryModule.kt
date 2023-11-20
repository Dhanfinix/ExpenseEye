package com.dhandev.rekapin.di

import com.dhandev.rekapin.domain.TransactionRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single{ TransactionRepository(get()) }
}