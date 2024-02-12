package com.sergiupuhalschi.networkservice.auth.mappers

import com.google.firebase.auth.FirebaseUser
import com.sergiupuhalschi.networkservice.auth.dto.CurrentUserDto

fun FirebaseUser.toDto(): CurrentUserDto {
    return CurrentUserDto(
        photoUrl = photoUrl?.toString(),
        displayName = displayName,
        email = email,
        phoneNumber = phoneNumber,
        providerId = providerId,
        uid = uid,
        isEmailVerified = isEmailVerified
    )
}