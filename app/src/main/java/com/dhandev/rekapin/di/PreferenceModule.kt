package com.dhandev.rekapin.di

import com.dhandev.rekapin.data.local.DataStorePreference
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val PreferenceModule = module {
    single {
        DataStorePreference(androidContext())
    }
}