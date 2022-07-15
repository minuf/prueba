package com.example

import com.example.local.LocalCharactersDataSource
import com.example.remote.RemoteCharactersDataSource
import com.marvel.entities.Character
import com.marvel.repositories.CharactersRepository

class CharactersRepositoryImpl(
    private val remoteDataSource: RemoteCharactersDataSource,
    private val localDataSource: LocalCharactersDataSource
) : CharactersRepository {
    override suspend fun getCharacters(size: Int, skip: Int): List<Character> {
        return remoteDataSource.getCharacters(size, skip)
    }

    override suspend fun getCharacterById(id: Int): Character {
        return remoteDataSource.getCharacterById(id)
    }
}