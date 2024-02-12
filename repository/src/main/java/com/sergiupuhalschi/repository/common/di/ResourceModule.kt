package com.sergiupuhalschi.repository.common.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.sergiupuhalschi.repository.person.resource.PersonResource
import org.koin.core.module.dsl.bind

internal val resourceModule = module {

    singleOf(::PersonResource) { bind<PersonResource>() }
}