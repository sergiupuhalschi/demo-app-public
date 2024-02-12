package com.sergiupuhalschi.networkservice.auth

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sergiupuhalschi.networkservice.auth.dto.CurrentUserDto
import com.sergiupuhalschi.networkservice.auth.mappers.toDto
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive

interface AuthService {

    suspend fun getCurrentUser(): CurrentUserDto?

    suspend fun signOut(): Flow<Unit>
}

internal class AuthServiceImplementation(
    private val context: Context
) : AuthService {

    override suspend fun getCurrentUser(): CurrentUserDto? {
        return Firebase.auth.currentUser?.toDto()
    }

    override suspend fun signOut(): Flow<Unit> {
        return callbackFlow {
            AuthUI.getInstance()
                .signOut(context)
                .addOnSuccessListener {
                    if (isActive) {
                        trySend(Unit)
                    }
                }
                .addOnFailureListener { cancel("Sign out failed", it) }
                .addOnCanceledListener { cancel() }
                .addOnCompleteListener { close() }

            awaitClose()
        }
    }
}