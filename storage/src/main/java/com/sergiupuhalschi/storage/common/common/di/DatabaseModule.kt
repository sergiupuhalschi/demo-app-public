package com.sergiupuhalschi.storage.common.common.di

import androidx.room.Room
import com.sergiupuhalschi.storage.common.common.db.DemoAppDatabase
import com.sergiupuhalschi.storage.common.common.person.PersonDao
import org.koin.dsl.module

internal val databaseModule = module {

    single<DemoAppDatabase> {
        Room.databaseBuilder(get(), DemoAppDatabase::class.java, "DemoAppDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    factory<PersonDao> { get<DemoAppDatabase>().userDao() }
}