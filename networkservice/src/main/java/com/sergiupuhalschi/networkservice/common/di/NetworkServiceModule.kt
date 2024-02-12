package com.sergiupuhalschi.networkservice.common.di

import com.sergiupuhalschi.networkservice.BuildConfig
import com.sergiupuhalschi.networkservice.auth.AuthService
import com.sergiupuhalschi.networkservice.auth.AuthServiceImplementation
import com.sergiupuhalschi.networkservice.common.utils.NetworkServiceFactory
import com.sergiupuhalschi.networkservice.person.PersonService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkServiceModule = module {

    includes(networkServiceUtilsModule)

    singleOf(::AuthServiceImplementation) { bind<AuthService>() }

    single<PersonService> { get<NetworkServiceFactory>().buildService(BuildConfig.MOCKY_API_URL) }
}