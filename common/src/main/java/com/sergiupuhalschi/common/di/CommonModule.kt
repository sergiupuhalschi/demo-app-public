package com.sergiupuhalschi.common.di

import com.sergiupuhalschi.common.dispatchers.DispatcherProvider
import com.sergiupuhalschi.common.dispatchers.DispatcherProviderImplementation
import com.sergiupuhalschi.common.utils.DateUtils
import com.sergiupuhalschi.common.utils.DateUtilsImplementation
import com.sergiupuhalschi.common.utils.NumberFormatUtils
import com.sergiupuhalschi.common.utils.NumberFormatUtilsImplementation
import com.sergiupuhalschi.common.utils.SerializerUtils
import com.sergiupuhalschi.common.utils.SerializerUtilsImplementation
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module

val commonModule = module {

    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single<DispatcherProvider> { DispatcherProviderImplementation() }

    single<SerializerUtils> { SerializerUtilsImplementation(get()) }

    single<DateUtils> { DateUtilsImplementation() }

    single<NumberFormatUtils> { NumberFormatUtilsImplementation() }
}