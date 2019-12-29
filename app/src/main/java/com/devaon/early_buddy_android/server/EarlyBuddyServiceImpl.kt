package com.devaon.early_buddy_android.server

import com.devaon.early_buddy_android.intercepter.CookiesIntercepter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EarlyBuddyServiceImpl {
    private const val BASE_URL = "http://15.164.70.24:3456/"

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(CookiesIntercepter())
            .addNetworkInterceptor(CookiesIntercepter()).build()

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service: EarlyBuddyService = retrofit.create(EarlyBuddyService::class.java)
}