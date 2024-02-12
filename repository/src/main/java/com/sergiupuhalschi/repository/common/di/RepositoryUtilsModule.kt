package com.sergiupuhalschi.repository.common.di

import com.sergiupuhalschi.common.utils.NetworkConnectivityHandler
import com.sergiupuhalschi.repository.common.connectivity.NetworkConnectivityHandlerImplementation
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryUtilsModule = module {

    singleOf(::NetworkConnectivityHandlerImplementation) { bind<NetworkConnectivityHandler>() }
}