package com.sergiupuhalschi.storage.common.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergiupuhalschi.storage.common.common.person.PersonDao
import com.sergiupuhalschi.storage.common.common.person.PersonEntity

@Database(
    entities = [
        PersonEntity::class
    ],
    version = 1,
    exportSchema = false
)
internal abstract class DemoAppDatabase : RoomDatabase() {

    abstract fun userDao(): PersonDao
}