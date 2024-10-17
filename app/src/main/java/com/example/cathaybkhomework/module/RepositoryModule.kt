package com.example.cathaybkhomework.module

import com.example.cathaybkhomework.repositories.AttractionsRepository
import com.example.cathaybkhomework.repositories.LanguageRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object RepositoryModule {
    val modules = module {
        single { AttractionsRepository(get()) }
        singleOf(::LanguageRepository)
    }
}