package com.example.cathaybkhomework

import com.example.cathaybkhomework.module.NetworkModule
import com.example.cathaybkhomework.retrofit.ApiService
import org.junit.jupiter.api.Test
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import kotlin.test.assertNotNull

class MyKoinTest: KoinTest {

    @Test
    fun `should inject my network components`() {
        startKoin {
            modules(
                NetworkModule.modules
            )
        }

        val apiService = get<ApiService>()

        assertNotNull(apiService)
    }
}