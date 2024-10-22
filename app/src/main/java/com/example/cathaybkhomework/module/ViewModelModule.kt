package com.example.cathaybkhomework.module

import com.example.cathaybkhomework.MainViewModel
import com.example.cathaybkhomework.page.activity.ActivityEventViewModel
import com.example.cathaybkhomework.page.attraction.list.AttractionViewModel
import com.example.cathaybkhomework.page.calendar.EventCalendarViewModel
import com.example.cathaybkhomework.page.home.HomeViewModel
import com.example.cathaybkhomework.page.tours.ToursViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val modules = module {
        viewModel { MainViewModel(get(), get()) }
        viewModel { HomeViewModel(get(), get()) }
        viewModel { AttractionViewModel(get(), get()) }
        viewModel { ActivityEventViewModel(get(), get()) }
        viewModel { EventCalendarViewModel(get(), get()) }
        viewModel { ToursViewModel(get(), get()) }
    }
}