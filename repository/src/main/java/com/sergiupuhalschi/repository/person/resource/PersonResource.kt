package com.sergiupuhalschi.repository.person.resource

import com.sergiupuhalschi.networkservice.common.utils.process
import com.sergiupuhalschi.networkservice.person.PersonService
import com.sergiupuhalschi.networkservice.person.dto.PersonDto
import com.sergiupuhalschi.repository.common.cache.CacheDataSource
import com.sergiupuhalschi.repository.common.resource.NetworkBoundResource
import com.sergiupuhalschi.repository.person.mappers.toEntity
import com.sergiupuhalschi.repository.person.mappers.toModel
import com.sergiupuhalschi.repository.person.models.Person
import com.sergiupuhalschi.storage.common.common.db.upsert
import com.sergiupuhalschi.storage.common.common.person.PersonDao
import com.sergiupuhalschi.storage.common.common.person.PersonEntity

class PersonResource(
    private val personCacheDataSource: CacheDataSource<List<PersonDto>>,
    private val personService: PersonService,
    private val personDao: PersonDao,
) : NetworkBoundResource<List<PersonDto>, List<PersonEntity>, List<Person>>() {

    override suspend fun getLocalData(): List<PersonEntity>? {
        return personDao.getPersons()
            .takeIf { it.isNotEmpty() }
    }

    override suspend fun getRemoteData(forceReload: Boolean): List<PersonDto> {
        return personCacheDataSource.getData(
            requestData = {
                personService.getPersons()
                    .process()
            },
            reload = forceReload
        )
    }

    override suspend fun mapLocalToOutput(data: List<PersonEntity>): List<Person> {
        return data.map { it.toModel() }
    }

    override suspend fun saveLocalData(data: List<PersonEntity>) {
        personDao.upsert(data)
    }

    override suspend fun mapRemoteToLocal(data: List<PersonDto>): List<PersonEntity> {
        return data.map { it.toEntity() }
    }
}