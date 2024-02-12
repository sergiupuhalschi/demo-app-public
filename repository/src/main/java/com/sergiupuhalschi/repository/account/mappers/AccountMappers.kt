package com.sergiupuhalschi.repository.account.mappers

import com.sergiupuhalschi.networkservice.auth.dto.CurrentUserDto
import com.sergiupuhalschi.repository.account.models.Account

fun CurrentUserDto.toModel(): Account {
    return Account(
        userId = uid,
        name = displayName ?: "",
        imageUrl = photoUrl ?: ""
    )
}