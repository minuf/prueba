package com.marvel.marvelApp

import android.app.Application
import com.marvel.marvelApp.di.AppModule
import com.marvel.marvelApp.di.DaggerGetAllCharactersComponent
import com.marvel.marvelApp.di.GetAllCharactersComponent

class App : Application() {
    private lateinit var getAllCharactersComponent: GetAllCharactersComponent

    override fun onCreate() {
        super.onCreate()
        getAllCharactersComponent = DaggerGetAllCharactersComponent.builder()
            .appModule(AppModule(this))
            .build()

    }

    fun getComponent() = getAllCharactersComponent
}