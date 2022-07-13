package com.example

import com.example.remote.RemoteCharactersDataSource
import com.marvel.entities.Character
import com.marvel.repository.CharactersRepository

class CharactersRepositoryImpl(
    private val remoteDataSource: RemoteCharactersDataSource,
    private val localDataSource: LocalCharactersDataSource
) : CharactersRepository {
    override suspend fun getCharacters(size: Int, skip: Int): List<Character> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterById(id: Int): Character {
        TODO("Not yet implemented")
    }
}