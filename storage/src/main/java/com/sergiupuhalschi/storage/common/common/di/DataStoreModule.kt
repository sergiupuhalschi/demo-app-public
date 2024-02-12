package com.sergiupuhalschi.storage.common.common.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sergiupuhalschi.storage.common.session.UserSessionDataStore
import com.sergiupuhalschi.storage.common.session.UserSessionDataStoreImplementation
import org.koin.dsl.module

internal val Context.userSessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

internal val dataStoreModule = module {

    single<UserSessionDataStore> { UserSessionDataStoreImplementation(get()) }
}