package com.example.remote

import com.example.remote.retrofit.services.CharactersService
import com.marvel.entities.Character

class RemoteCharactersDataSource constructor(private val charactersService: CharactersService) {

    suspend fun getCharacters(size: Int, skip: Int): List<Character> {
        val response =
            charactersService.fetchAllCharacters(size, skip)
        return response.body()?.data?.results ?: listOf()
    }

    suspend fun getCharacterById(id: Int): Character {
        val response =
            charactersService.fetCharacterById(id)
        return response.body()!!.data.results[0]
    }
}