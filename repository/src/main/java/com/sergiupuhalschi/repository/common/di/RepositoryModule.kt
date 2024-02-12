package com.sergiupuhalschi.repository.common.di

import com.sergiupuhalschi.repository.account.AccountRepository
import com.sergiupuhalschi.repository.account.AccountRepositoryImplementation
import com.sergiupuhalschi.repository.person.PersonRepository
import com.sergiupuhalschi.repository.person.PersonRepositoryImplementation
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {

    includes(
        repositoryUtilsModule,
        resourceModule,
        cacheDataSourceModule
    )

    singleOf(::AccountRepositoryImplementation) { bind<AccountRepository>() }

    singleOf(::PersonRepositoryImplementation) { bind<PersonRepository>() }
}