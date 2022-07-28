package com.example.testapp.di

import com.example.CharactersRepositoryImpl
import com.example.local.LocalCharactersDataSource
import com.example.local.room.CharacterDao
import com.example.remote.RemoteCharactersDataSource
import com.marvel.usecases.GetCharactersUseCase
import com.marvel.repositories.CharactersRepository
import com.example.remote.retrofit.services.CharactersService
import com.marvel.usecases.GetCharactersUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(charactersService: CharactersService): RemoteCharactersDataSource =
        RemoteCharactersDataSource(charactersService)

    @Provides
    @Singleton
    fun provideLocalDataSource(charactersDao: CharacterDao): LocalCharactersDataSource =
        LocalCharactersDataSource(charactersDao)

    @Provides
    @Singleton
    fun provideCharactersRepository(
        remoteCharactersDataSource: RemoteCharactersDataSource,
        localCharactersDataSource: LocalCharactersDataSource
    ): CharactersRepository =
        CharactersRepositoryImpl(remoteCharactersDataSource, localCharactersDataSource)

    @Provides
    fun provideGetAllCharacters(charactersRepository: CharactersRepository) : GetCharactersUseCase =
        GetCharactersUseCaseImpl(charactersRepository)
}