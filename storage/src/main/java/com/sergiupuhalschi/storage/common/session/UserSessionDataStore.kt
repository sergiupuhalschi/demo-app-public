package com.sergiupuhalschi.storage.common.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sergiupuhalschi.storage.common.common.di.userSessionDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val USER_ID_KEY by lazy { stringPreferencesKey("AUTH_TOKEN_KEY") }

interface UserSessionDataStore {

    suspend fun storeUserId(id: String)

    fun getUserId(): Flow<String?>

    suspend fun clear()
}

internal class UserSessionDataStoreImplementation(
    private val context: Context
) : UserSessionDataStore {

    private val userSessionDataStore: DataStore<Preferences> by lazy { context.userSessionDataStore }

    override suspend fun storeUserId(id: String) {
        userSessionDataStore.edit { session ->
            session[USER_ID_KEY] = id
        }
    }

    override fun getUserId(): Flow<String?> {
        return userSessionDataStore.data.map { session ->
            session[USER_ID_KEY]
        }
    }

    override suspend fun clear() {
        userSessionDataStore.edit { account ->
            account.clear()
        }
    }
}