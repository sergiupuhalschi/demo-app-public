package com.sergiupuhalschi.demoapp.app.di

import com.sergiupuhalschi.common.di.commonModule
import com.sergiupuhalschi.networkservice.common.di.networkServiceModule
import com.sergiupuhalschi.presentation.common.di.presentationModule
import com.sergiupuhalschi.repository.common.di.repositoryModule
import com.sergiupuhalschi.storage.common.common.di.storageModule
import org.koin.dsl.module

internal val appModule = module {
    includes(
        commonModule,
        presentationModule,
        repositoryModule,
        networkServiceModule,
        storageModule
    )
}