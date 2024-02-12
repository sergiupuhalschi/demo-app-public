package com.sergiupuhalschi.networkservice.person.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonDto(
    @Json(name = "id")
    val id: String?,
    @Json(name = "firstName")
    val firstName: String?,
    @Json(name = "lastName")
    val lastName: String?,
    @Json(name = "username")
    val username: String?,
    @Json(name = "picture")
    val picture: String?,
    @Json(name = "netWorth")
    val netWorth: Double?
)
