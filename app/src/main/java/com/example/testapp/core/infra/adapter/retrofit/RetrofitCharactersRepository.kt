package com.example.testapp.core.infra.adapter.retrofit

import com.example.domain.Character
import com.example.testapp.core.domain.repository.CharactersRepository
import com.example.testapp.core.infra.adapter.retrofit.services.CharactersService

class RetrofitCharactersRepository constructor(private val charactersService: CharactersService) :
    CharactersRepository {

    override suspend fun getCharacters(size: Int, skip: Int): List<Character> {
        val response =
            charactersService.fetchAllCharacters(limit = size, offset = skip)
        return response.body()?.data?.results ?: listOf()
    }

    override suspend fun getCharacterById(id: Int): Character {
        val response =
            charactersService.fetCharacterById(id)
        return response.body()!!.data.results[0]
    }
}