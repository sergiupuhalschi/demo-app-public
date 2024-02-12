package com.sergiupuhalschi.presentation.common.di

import com.sergiupuhalschi.presentation.ui.home.tab.mappers.PersonViewDataMapper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val presentationMappersModule = module {

    singleOf(::PersonViewDataMapper)
}