package com.sergiupuhalschi.repository.person.mappers

import com.sergiupuhalschi.networkservice.person.dto.PersonDto
import com.sergiupuhalschi.repository.person.models.Person
import com.sergiupuhalschi.storage.common.common.person.PersonEntity

fun PersonDto.toEntity(): PersonEntity {
    return PersonEntity(
        id = id ?: "",
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        username = username ?: "",
        picture = picture ?: "",
        netWorth = netWorth ?: 0.0
    )
}

fun PersonEntity.toModel(): Person {
    return Person(
        id = id,
        firstName = firstName,
        lastName = lastName,
        username = username,
        picture = picture,
        netWorth = netWorth
    )
}