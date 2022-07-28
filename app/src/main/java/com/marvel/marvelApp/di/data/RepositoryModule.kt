package com.marvel.marvelApp.di.data

import com.marvel.CharactersRepositoryImpl
import com.marvel.local.LocalCharactersDataSource
import com.marvel.remote.RemoteCharactersDataSource
import com.marvel.repositories.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class RepositoryModule {

    @Provides
    fun provideCharactersRepository(
        remoteCharactersDataSource: RemoteCharactersDataSource,
        localCharactersDataSource: LocalCharactersDataSource
    ): CharactersRepository =
        CharactersRepositoryImpl(remoteCharactersDataSource, localCharactersDataSource)
}