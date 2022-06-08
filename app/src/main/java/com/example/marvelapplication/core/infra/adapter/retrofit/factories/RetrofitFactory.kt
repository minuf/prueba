package com.example.marvelapplication.core.infra.adapter.retrofit.factories

import com.example.marvelapplication.core.infra.adapter.retrofit.services.CharactersService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val baseUrl = "https://gateway.marvel.com:443/v1/public/"
    private val retrofit: Retrofit by lazy { makeRetrofit() }

    private fun makeRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    fun makeCharactersService(): CharactersService = retrofit.create(CharactersService::class.java)
}