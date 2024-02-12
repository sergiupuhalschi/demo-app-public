package com.sergiupuhalschi.networkservice.person

import com.sergiupuhalschi.networkservice.common.models.NetworkResponse
import com.sergiupuhalschi.networkservice.person.dto.PersonDto
import retrofit2.http.GET

interface PersonService {

    @GET("v3/40bdda34-315c-4376-bcc3-ab4879e41c89")
    suspend fun getPersons(): NetworkResponse<List<PersonDto>, Any>
}