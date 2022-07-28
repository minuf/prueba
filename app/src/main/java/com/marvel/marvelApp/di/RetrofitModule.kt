package com.marvel.marvelApp.di

import com.marvel.remote.retrofit.factories.RetrofitFactory
import com.marvel.remote.retrofit.services.CharactersService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit() = RetrofitFactory().makeRetrofit()

    @Provides
    @Singleton
    fun provideRetrofitCharacterService(retrofit: Retrofit): CharactersService =
        retrofit.create(CharactersService::class.java)
}