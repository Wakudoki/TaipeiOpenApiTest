package com.example.cathaybkhomework

import android.app.Application
import com.example.cathaybkhomework.common.module.NetworkModule
import com.example.cathaybkhomework.common.module.RepositoryModule
import com.example.cathaybkhomework.common.module.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                NetworkModule.modules,
                RepositoryModule.modules,
                ViewModelModule.modules
            )
        }
    }
}