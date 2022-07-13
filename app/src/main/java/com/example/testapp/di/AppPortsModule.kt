package com.example.testapp.di

import com.marvel.usecases.GetCharacters
import com.marvel.repository.CharactersRepository
import com.example.remote.retrofit.RetrofitCharactersRepository
import com.example.remote.retrofit.services.CharactersService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppPortsModule {

    @Provides
    @Singleton
    fun provideCharactersRepository(charactersService: CharactersService): CharactersRepository =
        RetrofitCharactersRepository(charactersService)

    @Provides
    fun provideGetAllCharacters(charactersRepository: CharactersRepository) =
        GetCharacters(charactersRepository)
}