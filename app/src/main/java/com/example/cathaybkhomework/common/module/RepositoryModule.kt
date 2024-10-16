package com.example.cathaybkhomework.common.module

import com.example.cathaybkhomework.repositories.AttractionsRepository
import org.koin.dsl.module

object RepositoryModule {
    val modules = module {
        single { AttractionsRepository(get()) }
    }
}