package com.sergiupuhalschi.presentation.common.di

import com.sergiupuhalschi.presentation.common.utils.ResourceProvider
import com.sergiupuhalschi.presentation.common.utils.ResourceProviderImplementation
import com.sergiupuhalschi.presentation.ui.app.utils.AppStateHandler
import com.sergiupuhalschi.presentation.ui.app.utils.ThrowableToAlertMapper
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val presentationUtilsModule = module {

    singleOf(::ResourceProviderImplementation) { bind<ResourceProvider>() }

    singleOf(::ThrowableToAlertMapper)

    singleOf(::AppStateHandler)
}