package com.sergiupuhalschi.repository.person.models

data class Person(
    val id: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val picture: String,
    val netWorth: Double
) {

    fun getFullName(): String {
        return "$firstName $lastName"
    }
}
