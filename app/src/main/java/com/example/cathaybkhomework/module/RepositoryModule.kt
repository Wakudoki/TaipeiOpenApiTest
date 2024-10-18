package com.example.cathaybkhomework.module

import com.example.cathaybkhomework.repositories.TravelApiRepository
import com.example.cathaybkhomework.repositories.LanguageRepository
import com.example.cathaybkhomework.repositories.ThemeModeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object RepositoryModule {
    val modules = module {
        single { TravelApiRepository(get()) }
        singleOf(::LanguageRepository)
        singleOf(::ThemeModeRepository)
    }
}