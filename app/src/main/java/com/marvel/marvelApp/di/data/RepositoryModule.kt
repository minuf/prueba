package com.marvel.marvelApp.di.data

import com.marvel.CharactersRepositoryImpl
import com.marvel.domain.model.errors.ErrorHandler
import com.marvel.local.LocalCharactersDataSource
import com.marvel.remote.RemoteCharactersDataSource
import com.marvel.domain.repositories.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideCharactersRepository(
        remoteCharactersDataSource: RemoteCharactersDataSource,
        localCharactersDataSource: LocalCharactersDataSource,
        errorHandler: ErrorHandler
    ): CharactersRepository =
        CharactersRepositoryImpl(
            remoteCharactersDataSource,
            localCharactersDataSource,
            errorHandler
        )
}