package com.sergiupuhalschi.demoapp

import android.content.Context
import com.sergiupuhalschi.demoapp.app.di.appModule
import com.sergiupuhalschi.repository.common.cache.CachePeriod
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class CheckModulesTest : KoinTest {

    @Test
    fun checkAllModules() {
        appModule.verify(
            extraTypes = listOf(
                Context::class,
                CachePeriod::class
            )
        )
    }
}