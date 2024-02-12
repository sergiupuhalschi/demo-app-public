package com.sergiupuhalschi.storage.common.common.person

import androidx.room.Dao
import androidx.room.Query
import com.sergiupuhalschi.storage.common.common.db.BaseDao

@Dao
interface PersonDao : BaseDao<PersonEntity> {

    @Query("select * from PersonEntity")
    suspend fun getPersons(): List<PersonEntity>

    @Query("delete from PersonEntity")
    suspend fun deleteAll()
}