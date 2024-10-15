package com.example.cathaybkhomework.common.module

import com.example.cathaybkhomework.common.MyConst
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

object CoreModule {
    operator fun invoke() = module {
        single {
            val builder = OkHttpClient.Builder()
                .connectTimeout(MyConst.HTTP_TIMEOUT.inWholeMilliseconds, TimeUnit.MILLISECONDS)
                .readTimeout(MyConst.HTTP_TIMEOUT.inWholeMilliseconds, TimeUnit.MILLISECONDS)

            //建立攔截器，方便查看request & response
            listOf(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            ).forEach {
                builder.addInterceptor(it)
            }

            builder.build()
        }
    }
}