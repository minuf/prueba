package com.example.testapp

import android.app.Application
import com.example.testapp.di.DaggerGetAllCharactersComponent
import com.example.testapp.di.GetAllCharactersComponent

class App : Application() {
    private lateinit var getAllCharactersComponent: GetAllCharactersComponent

    override fun onCreate() {
        super.onCreate()
        getAllCharactersComponent = DaggerGetAllCharactersComponent.builder().build()

    }

    fun getComponent() = getAllCharactersComponent
}