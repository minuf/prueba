package com.marvel.marvelApp.di.usecases

import com.marvel.domain.repositories.CharactersRepository
import com.marvel.domain.usecases.GetCharacterByIdUseCase
import com.marvel.domain.usecases.GetCharacterByIdUseCaseImpl
import com.marvel.domain.usecases.GetCharactersUseCase
import com.marvel.domain.usecases.GetCharactersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    fun provideGetAllCharactersUseCase(charactersRepository: CharactersRepository): GetCharactersUseCase =
        GetCharactersUseCaseImpl(charactersRepository)

    @Provides
    fun provideGetCharacterByIdUseCase(charactersRepository: CharactersRepository): GetCharacterByIdUseCase =
        GetCharacterByIdUseCaseImpl(charactersRepository)
}