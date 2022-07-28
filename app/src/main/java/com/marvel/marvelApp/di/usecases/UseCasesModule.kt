package com.marvel.marvelApp.di.usecases

import com.marvel.repositories.CharactersRepository
import com.marvel.usecases.GetCharactersUseCase
import com.marvel.usecases.GetCharactersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class UseCasesModule {

    @Provides
    fun provideGetAllCharactersUseCase(charactersRepository: CharactersRepository) : GetCharactersUseCase =
        GetCharactersUseCaseImpl(charactersRepository)
}