package com.sergiupuhalschi.networkservice.auth.dto

data class CurrentUserDto(
    val photoUrl: String?,
    val displayName: String?,
    val email: String?,
    val phoneNumber: String?,
    val providerId: String,
    val uid: String,
    val isEmailVerified: Boolean
)
