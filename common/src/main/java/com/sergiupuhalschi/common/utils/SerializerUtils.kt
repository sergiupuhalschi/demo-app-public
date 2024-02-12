package com.sergiupuhalschi.common.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import timber.log.Timber

interface SerializerUtils {

    fun <T> fromJson(json: String, clazz: Class<T>): T?

    fun <T> toJson(src: T, clazz: Class<T>): String

    fun <T> toJsonArray(src: List<T>?, clazz: Class<T>): String

    fun <T> fromJsonArray(json: String, clazz: Class<T>): List<T>

    fun <T, U> fromJsonMap(json: String, keyClass: Class<T>, valueClass: Class<U>): Map<T, U>
}

internal class SerializerUtilsImplementation(
    private val moshi: Moshi
) : SerializerUtils {

    override fun <T> fromJson(json: String, clazz: Class<T>): T? {
        return try {
            if (json.isBlank()) {
                null
            } else {
                moshi.adapter(clazz).fromJson(json)
            }
        } catch (ex: Throwable) {
            Timber.e(ex)
            null
        }
    }

    override fun <T> toJson(src: T, clazz: Class<T>): String {
        return try {
            moshi.adapter(clazz).toJson(src)
        } catch (ex: Throwable) {
            Timber.e(ex)
            ""
        }
    }

    override fun <T> fromJsonArray(json: String, clazz: Class<T>): List<T> {
        return try {
            if (json.isBlank()) {
                emptyList()
            } else {
                val type = Types.newParameterizedType(List::class.java, clazz)
                moshi.adapter<List<T>>(type)
                    .fromJson(json) ?: emptyList()
            }
        } catch (ex: Throwable) {
            Timber.e(ex)
            emptyList()
        }
    }

    override fun <T> toJsonArray(src: List<T>?, clazz: Class<T>): String {
        return try {
            if (src == null) {
                ""
            } else {
                val type = Types.newParameterizedType(List::class.java, clazz)
                moshi.adapter<List<T>>(type)
                    .toJson(src)
            }
        } catch (ex: Throwable) {
            Timber.e(ex)
            ""
        }
    }

    override fun <T, U> fromJsonMap(json: String, keyClass: Class<T>, valueClass: Class<U>): Map<T, U> {
        return try {
            if (json.isBlank()) {
                emptyMap()
            } else {
                val type = Types.newParameterizedType(Map::class.java, keyClass, valueClass)
                moshi.adapter<Map<T, U>>(type)
                    .fromJson(json) ?: emptyMap()
            }
        } catch (ex: Throwable) {
            Timber.e(ex)
            emptyMap()
        }
    }
}