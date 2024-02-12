package com.sergiupuhalschi.presentation.common.di

import org.koin.dsl.module

val presentationModule = module {

    includes(
        viewModelsModule,
        presentationUtilsModule,
        presentationMappersModule
    )
}