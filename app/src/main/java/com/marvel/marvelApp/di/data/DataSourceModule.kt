package com.marvel.marvelApp.di.data

import com.marvel.local.LocalCharactersDataSource
import com.marvel.local.LocalCharactersDataSourceImpl
import com.marvel.local.room.CharacterDao
import com.marvel.remote.RemoteCharactersDataSource
import com.marvel.remote.RemoteCharactersDataSourceImpl
import com.marvel.remote.retrofit.services.CharactersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class DataSourceModule {
    @Provides
    fun provideRemoteDataSource(charactersService: CharactersService): RemoteCharactersDataSource =
        RemoteCharactersDataSourceImpl(charactersService)

    @Provides
    fun provideLocalDataSource(charactersDao: CharacterDao): LocalCharactersDataSource =
        LocalCharactersDataSourceImpl(charactersDao)
}