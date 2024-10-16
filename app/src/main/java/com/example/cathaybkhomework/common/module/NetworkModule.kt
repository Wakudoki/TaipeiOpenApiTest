package com.example.cathaybkhomework.common.module

import com.example.cathaybkhomework.common.MyConst
import com.example.cathaybkhomework.retrofit.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    val modules = module {
        single { provideHttpClient() }
        single { provideConverterFactory() }
        single { provideRetrofit(get(),get()) }
        single { provideService(get()) }
    }

    private fun provideHttpClient(): OkHttpClient {
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

        return builder.build()
    }


    private fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()


    private fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MyConst.BASE_URL_GET)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    private fun provideService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}