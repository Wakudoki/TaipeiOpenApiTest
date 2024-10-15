package com.example.cathaybkhomework

import android.app.Application
import com.example.cathaybkhomework.common.module.CoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            CoreModule.invoke()
        }
    }
}