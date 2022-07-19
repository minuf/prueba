package com.example.testapp.di

import com.example.testapp.ui.characters.CharactersViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, UseCasesModule::class, RetrofitModule::class, RoomModule::class])
interface GetAllCharactersComponent {

    fun inject(charactersViewModel: CharactersViewModel)
}