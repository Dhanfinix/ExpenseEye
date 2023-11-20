package com.dhandev.rekapin

import android.app.Application
import com.dhandev.rekapin.di.PreferenceModule
import com.dhandev.rekapin.di.DatabaseModule
import com.dhandev.rekapin.di.RepositoryModule
import com.dhandev.rekapin.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RekapinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@RekapinApplication)
            modules(
                listOf(
                    PreferenceModule,
                    DatabaseModule,
                    RepositoryModule,
                    ViewModelModule
                )
            )
        }
    }
}
