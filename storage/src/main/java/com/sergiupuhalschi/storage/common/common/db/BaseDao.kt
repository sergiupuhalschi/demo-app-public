package com.sergiupuhalschi.storage.common.common.db

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update
import timber.log.Timber

interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     * @return The SQLite row id
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Transaction
    suspend fun insert(obj: T): Long

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     * @return The SQLite row ids
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Transaction
    suspend fun insert(obj: List<T>): List<Long>

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    @Transaction
    suspend fun update(obj: T)

    /**
     * Update an array of objects from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    @Transaction
    suspend fun update(obj: List<T>)

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    suspend fun delete(obj: T)

    @Delete
    suspend fun delete(obj: List<T>)
}

@Transaction
suspend fun <T> BaseDao<T>.upsert(objList: List<T>) {
    Timber.d("Upsert: list start")

    val insertResult = insert(objList)
    val updateList = ArrayList<T>()

    for (i in insertResult.indices) {
        if (insertResult[i] == -1L) {
            updateList.add(objList[i])
        }
    }

    if (updateList.isNotEmpty()) {
        update(updateList)
    }

    Timber.d("Upsert: list end")
}

@Transaction
suspend fun <T> BaseDao<T>.upsert(obj: T) {
    Timber.d("Upsert: start")

    val id = insert(obj)
    if (id == -1L) {
        Timber.d("Insert ignored. Updating..")
        update(obj)
    }

    Timber.d("Upsert: end")
}