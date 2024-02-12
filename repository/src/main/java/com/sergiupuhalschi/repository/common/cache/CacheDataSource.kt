package com.sergiupuhalschi.repository.common.cache

import com.sergiupuhalschi.common.utils.DateUtils
import java.util.concurrent.TimeUnit

enum class CachePeriod(val seconds: Long) {

    VERY_SHORT(10),
    SHORT(TimeUnit.MINUTES.toSeconds(1)),
    MEDIUM(TimeUnit.MINUTES.toSeconds(5)),
    LONG(TimeUnit.HOURS.toSeconds(1))
}

class CacheDataSource<T>(
    private val dateUtils: DateUtils,
    private val period: CachePeriod = CachePeriod.SHORT
) {

    private var data: T? = null
    private var cachedTimestamp: Long = 0

    suspend fun getData(requestData: suspend () -> T, reload: Boolean = false): T {
        return getNullableData(requestData, reload) ?: throw IllegalStateException("The data can't be null")
    }

    suspend fun getNullableData(requestData: suspend () -> T?, reload: Boolean = false): T? {
        return data?.takeIf {
            !reload && dateUtils.getCurrentTimeEpoch() - cachedTimestamp < period.seconds
        } ?: run {
            val newData = requestData()
            data = newData
            cachedTimestamp = dateUtils.getCurrentTimeEpoch()
            newData
        }
    }

    fun getCachedData(): T? {
        return data
    }

    fun clear() {
        data = null
        cachedTimestamp = 0
    }
}