package com.sergiupuhalschi.networkservice.common.di

import android.content.Context
import com.sergiupuhalschi.networkservice.common.adapters.NetworkResponseAdapterFactory
import com.sergiupuhalschi.networkservice.common.connectivity.NetworkConnectivityService
import com.sergiupuhalschi.networkservice.common.connectivity.NetworkConnectivityServiceImplementation
import com.sergiupuhalschi.networkservice.common.utils.NetworkServiceFactory
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val networkServiceUtilsModule = module {

    factory<Retrofit.Builder> {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(get<Moshi>()))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
    }

    factory<OkHttpClient.Builder> {
        OkHttpClient.Builder()
            .apply {
                val context = get<Context>()
                val cacheDir = File(context.cacheDir.absolutePath, context.packageName)
                cache(Cache(cacheDir, (10 * 1024 * 1024).toLong()))
                readTimeout(30, TimeUnit.SECONDS)
                connectTimeout(30, TimeUnit.SECONDS)
            }
    }

    factoryOf(::NetworkServiceFactory)

    singleOf(::NetworkConnectivityServiceImplementation) { bind<NetworkConnectivityService>() }
}