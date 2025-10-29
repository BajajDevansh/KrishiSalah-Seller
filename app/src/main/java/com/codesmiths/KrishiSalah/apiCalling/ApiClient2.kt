package com.codesmiths.KrishiSalah.apiCalling

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object ApiClient2 {
    const val BASE_URL="https://fertilizer-recommendation-api-4voi.onrender.com/"
    fun create(context: Context): ApiService2{
        val client=OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService2::class.java)
    }
}