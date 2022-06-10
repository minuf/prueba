package com.example.marvelapplication.core.infra.adapter.retrofit.factories

import com.example.marvelapplication.core.infra.adapter.retrofit.services.CharactersService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory {
    private val baseUrl = "https://gateway.marvel.com:443/v1/public/"
    private val retrofit: Retrofit by lazy { makeRetrofit() }

    private fun makeRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)
        client.connectTimeout(10, TimeUnit.SECONDS);
        client.readTimeout(30, TimeUnit.SECONDS);

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    fun makeCharactersService(): CharactersService = retrofit.create(CharactersService::class.java)
}