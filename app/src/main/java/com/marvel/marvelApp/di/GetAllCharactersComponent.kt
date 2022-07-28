package com.marvel.marvelApp.di

import com.marvel.marvelApp.ui.characters.CharactersViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, UseCasesModule::class, RetrofitModule::class, RoomModule::class])
interface GetAllCharactersComponent {

    fun inject(charactersViewModel: CharactersViewModel)
}