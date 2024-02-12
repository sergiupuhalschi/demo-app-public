package com.sergiupuhalschi.storage.common.common.di

import org.koin.dsl.module

val storageModule = module {

    includes(
        dataStoreModule,
        databaseModule
    )
}