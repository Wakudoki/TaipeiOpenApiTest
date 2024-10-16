package com.example.cathaybkhomework.common.module

import com.example.cathaybkhomework.page.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val modules = module {
        viewModel { HomeViewModel(get()) }
    }
}