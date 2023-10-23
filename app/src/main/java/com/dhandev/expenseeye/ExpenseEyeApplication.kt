package com.dhandev.expenseeye

import android.app.Application
import com.dhandev.expenseeye.di.PreferenceModule
import com.dhandev.expenseeye.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ExpenseEyeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@ExpenseEyeApplication)
            modules(
                listOf(
                    PreferenceModule,
                    ViewModelModule
                )
            )
        }
    }
}
