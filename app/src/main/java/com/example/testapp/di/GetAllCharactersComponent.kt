package com.example.testapp.di

import com.example.testapp.MainActivity
import com.example.testapp.di.AppPortsModule
import com.example.testapp.di.RetrofitModule
import com.example.testapp.ui.characters.CharactersViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppPortsModule::class, RetrofitModule::class])
interface GetAllCharactersComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(charactersViewModel: CharactersViewModel)
}