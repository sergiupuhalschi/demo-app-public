package com.sergiupuhalschi.repository.account

import com.sergiupuhalschi.common.dispatchers.DispatcherProvider
import com.sergiupuhalschi.networkservice.auth.AuthService
import com.sergiupuhalschi.repository.account.mappers.toModel
import com.sergiupuhalschi.repository.account.models.Account
import com.sergiupuhalschi.storage.common.session.UserSessionDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface AccountRepository {

    suspend fun onSignedIn()

    fun observeSignedIn(): Flow<Boolean>

    suspend fun isSignedIn(): Boolean

    suspend fun signOut()

    suspend fun getAccount(): Account
}

internal class AccountRepositoryImplementation(
    private val authService: AuthService,
    private val userSessionDataStore: UserSessionDataStore,
    private val dispatcherProvider: DispatcherProvider
) : AccountRepository {

    override suspend fun onSignedIn() {
        val user = authService.getCurrentUser() ?: throw IllegalStateException("Unable to get the current user info during sign in")
        userSessionDataStore.storeUserId(user.uid)
    }

    override fun observeSignedIn(): Flow<Boolean> {
        return userSessionDataStore.getUserId()
            .map { !it.isNullOrEmpty() }
            .flowOn(dispatcherProvider.io)
    }

    override suspend fun isSignedIn(): Boolean {
        return withContext(dispatcherProvider.io) {
            authService.getCurrentUser() != null
        }
    }

    override suspend fun signOut() {
        withContext(dispatcherProvider.io) {
            authService.signOut().firstOrNull() ?: throw IllegalStateException("Unexpected error happened during sign-out")
            userSessionDataStore.clear()
        }
    }

    override suspend fun getAccount(): Account {
        return withContext(dispatcherProvider.io) {
            authService.getCurrentUser()
                ?.toModel()
                ?: throw IllegalStateException("Retrieving account requires signing in")
        }
    }
}