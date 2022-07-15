package com.example.testapp.di

import com.example.CharactersRepositoryImpl
import com.example.local.LocalCharactersDataSource
import com.example.remote.RemoteCharactersDataSource
import com.marvel.usecases.GetCharacters
import com.marvel.repositories.CharactersRepository
import com.example.remote.retrofit.services.CharactersService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(charactersService: CharactersService) : RemoteCharactersDataSource =
        RemoteCharactersDataSource(charactersService)

    @Provides
    @Singleton
    fun provideCharactersRepository(remoteCharactersDataSource: RemoteCharactersDataSource): CharactersRepository =
        CharactersRepositoryImpl(remoteCharactersDataSource, LocalCharactersDataSource())

    @Provides
    fun provideGetAllCharacters(charactersRepository: CharactersRepository) =
        GetCharacters(charactersRepository)
}