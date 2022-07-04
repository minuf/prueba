package com.example.testapp.di

import com.example.testapp.core.domain.ports.GetCharacters
import com.example.testapp.core.domain.repository.CharactersRepository
import com.example.testapp.core.infra.adapter.retrofit.RetrofitCharactersRepository
import com.example.testapp.core.infra.adapter.retrofit.services.CharactersService
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