package com.sergiupuhalschi.repository.common.resource

import com.sergiupuhalschi.repository.common.utils.ResourceNotFoundException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundResource<RemoteType, LocalType, OutputType> {

    abstract suspend fun getLocalData(): LocalType?

    abstract suspend fun getRemoteData(forceReload: Boolean): RemoteType?

    abstract suspend fun mapRemoteToLocal(data: RemoteType): LocalType

    abstract suspend fun saveLocalData(data: LocalType)

    abstract suspend fun mapLocalToOutput(data: LocalType): OutputType

    suspend fun asOutput(
        shouldFetch: Boolean,
        forceReloadRemote: Boolean = shouldFetch,
        default: (() -> OutputType)? = null
    ): OutputType {
        val output = if (shouldFetch) {
            proceed(forceReloadRemote)
        } else {
            val localData = getLocalData()

            if (localData == null) {
                proceed(true)
            } else {
                mapLocalToOutput(localData)
            }
        }

        return output ?: if (default != null) {
            default.invoke()
        } else {
            throw ResourceNotFoundException()
        }
    }

    fun asFlow(
        shouldFetch: Boolean,
        forceReloadRemote: Boolean = shouldFetch
    ): Flow<OutputType> {
        return flow {
            val localData = getLocalData()

            if (localData != null) {
                emit(mapLocalToOutput(localData))
            }

            if (shouldFetch || localData == null) {
                delay(300)
                emit(proceed(forceReloadRemote) ?: throw ResourceNotFoundException())
            }
        }
    }

    private suspend fun proceed(forceReload: Boolean): OutputType? {
        return getRemoteData(forceReload)?.let { data ->
            val localData = mapRemoteToLocal(data)
            saveLocalData(localData)
            mapLocalToOutput(localData)
        }
    }
}