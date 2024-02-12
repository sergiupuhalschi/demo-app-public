package com.sergiupuhalschi.repository.common.di

import com.sergiupuhalschi.common.utils.DateUtils
import com.sergiupuhalschi.networkservice.person.dto.PersonDto
import com.sergiupuhalschi.repository.common.cache.CacheDataSource
import com.sergiupuhalschi.repository.common.cache.CachePeriod
import org.koin.dsl.module

internal val cacheDataSourceModule = module {

    single<CacheDataSource<List<PersonDto>>> { CacheDataSource(get<DateUtils>(), CachePeriod.VERY_SHORT) }
}