package com.example.marvelapplication.core.infra.adapter.retrofit

import com.example.marvelapplication.core.domain.model.Character
import com.example.marvelapplication.core.domain.repository.CharactersRepository
import com.example.marvelapplication.core.infra.adapter.retrofit.data.VALUES
import com.example.marvelapplication.core.infra.adapter.retrofit.services.CharactersService

class RetrofitCharactersRepository(private val charactersService: CharactersService) :
    CharactersRepository {

    override suspend fun getAllCharacters(): List<Character> {
        val response =
            charactersService.fetchAllCharacters()
        return response.body()?.data?.results ?: listOf()
    }

    override suspend fun getCharacterById(id: Int): Character {
        val response =
            charactersService.fetCharacterById(id)
        return response.body()!!.data.results[0]
    }
}