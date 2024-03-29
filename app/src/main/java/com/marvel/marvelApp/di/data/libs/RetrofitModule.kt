package com.marvel.marvelApp.di.data.libs

import com.marvel.remote.retrofit.factories.RetrofitFactory
import com.marvel.remote.retrofit.services.CharactersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideRetrofit() = RetrofitFactory().makeRetrofit()

    @Provides
    fun provideRetrofitCharacterService(retrofit: Retrofit): CharactersService =
        retrofit.create(CharactersService::class.java)
}