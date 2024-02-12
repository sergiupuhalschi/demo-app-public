package com.sergiupuhalschi.demoapp.app

import android.app.Application
import com.sergiupuhalschi.common.di.commonModule
import com.sergiupuhalschi.demoapp.BuildConfig
import com.sergiupuhalschi.demoapp.app.di.appModule
import com.sergiupuhalschi.networkservice.common.di.networkServiceModule
import com.sergiupuhalschi.presentation.common.di.presentationModule
import com.sergiupuhalschi.repository.common.di.repositoryModule
import com.sergiupuhalschi.storage.common.common.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupLogging()
        setupDI()
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupDI() {
        startKoin {
            androidLogger()
            androidContext(this@DemoApplication)
            modules(appModule)
        }
    }
}