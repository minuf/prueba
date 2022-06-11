package com.example.testapp.core.domain.ports

import com.example.marvelapplication.core.infra.adapter.retrofit.RetrofitCharactersRepository
import com.example.marvelapplication.core.infra.adapter.retrofit.factories.RetrofitFactory

class AppPortsFactory {
    private val retrofitCharactersRepository = RetrofitCharactersRepository(RetrofitFactory().makeCharactersService())
    val getAllCharacters = GetAllCharacters(retrofitCharactersRepository)
    val getCharacterById = GetCharacterById(retrofitCharactersRepository)
}