package com.marvel.remote

import com.marvel.remote.retrofit.services.CharactersService
import com.marvel.util.toDomainModel
import com.marvel.domain.model.Character

interface RemoteCharactersDataSource {
    suspend fun getCharacters(size: Int, skip: Int): List<Character>
    suspend fun getCharacterById(id: Int): Character
}

class RemoteCharactersDataSourceImpl(private val charactersService: CharactersService) :
    RemoteCharactersDataSource {

    override suspend fun getCharacters(size: Int, skip: Int): List<Character> {
        val response =
            charactersService.fetchAllCharacters(size, skip)
        return response.body()?.data?.results?.map { it.toDomainModel() } ?: listOf()
    }

    override suspend fun getCharacterById(id: Int): Character {
        val response =
            charactersService.fetCharacterById(id)
        return response.body()?.data?.results?.get(0)?.toDomainModel() ?: throw Exception("CHARACTER NOT FOUND") //TODO: CREATE ERROR FOR THIS CASE
    }
}