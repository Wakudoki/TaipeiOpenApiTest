package com.example.cathaybkhomework.module

import com.example.cathaybkhomework.MainViewModel
import com.example.cathaybkhomework.page.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val modules = module {
        viewModel { MainViewModel(get(), get()) }
        viewModel { HomeViewModel(get(), get()) }
    }
}