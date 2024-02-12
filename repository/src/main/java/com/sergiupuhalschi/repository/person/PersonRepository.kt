package com.sergiupuhalschi.repository.person

import com.sergiupuhalschi.common.dispatchers.DispatcherProvider
import com.sergiupuhalschi.repository.person.models.Person
import com.sergiupuhalschi.repository.person.resource.PersonResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

interface PersonRepository {

    suspend fun fetchPersons(): List<Person>

    suspend fun getPersons(): Flow<List<Person>>
}

internal class PersonRepositoryImplementation(
    private val personResource: PersonResource,
    private val dispatcherProvider: DispatcherProvider
) : PersonRepository {

    override suspend fun fetchPersons(): List<Person> {
        return withContext(dispatcherProvider.io) {
            personResource.asOutput(
                shouldFetch = true,
                forceReloadRemote = false
            )
        }
    }

    override suspend fun getPersons(): Flow<List<Person>> {
        return personResource.asFlow(true)
            .flowOn(dispatcherProvider.io)
    }
}