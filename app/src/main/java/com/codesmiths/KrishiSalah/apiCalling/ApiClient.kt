package com.codesmiths.KrishiSalah.apiCalling

import android.content.Context

object ApiClient {
    private const val BASE_URL="https://b4e0932317c8.ngrok-free.app/"

    fun create(context: Context): ApiService{
        val tokenManager= TokenManager.getInstance(context)
        val logging=okhttp3.logging.HttpLoggingInterceptor().apply {
            level=okhttp3.logging.HttpLoggingInterceptor.Level.BODY
        }
        val client=okhttp3.OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .addInterceptor(logging)
            .build()
        return retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}