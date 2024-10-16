package com.example.cathaybkhomework

import android.content.Context
import org.koin.mp.KoinPlatform.getKoin

object MyApp {
    val koinContext get() = getKoin().get<Context>()
}