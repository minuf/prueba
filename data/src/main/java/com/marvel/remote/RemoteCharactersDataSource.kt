package com.marvel.remote

import com.marvel.remote.retrofit.services.CharactersService
import com.marvel.util.toDomainModel
import com.marvel.model.Character

class RemoteCharactersDataSource constructor(private val charactersService: CharactersService) {

    suspend fun getCharacters(size: Int, skip: Int): List<Character> {
        val response =
            charactersService.fetchAllCharacters(size, skip)
        return response.body()?.data?.results?.map { it.toDomainModel() } ?: listOf()
    }

    suspend fun getCharacterById(id: Int): Character {
        val response =
            charactersService.fetCharacterById(id)
        return response.body()!!.data.results[0].toDomainModel()
    }
}