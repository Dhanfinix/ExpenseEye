package com.dhandev.expenseeye.di

import com.dhandev.expenseeye.data.local.DataStorePreference
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val PreferenceModule = module {
    single {
        DataStorePreference(androidContext())
    }
}