package com.example.remote.retrofit

import com.marvel.repository.CharactersRepository
import com.example.remote.retrofit.services.CharactersService
import com.marvel.entities.Character

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