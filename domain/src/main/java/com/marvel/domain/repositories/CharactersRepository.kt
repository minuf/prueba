package com.marvel.domain.repositories

import com.marvel.domain.model.Character
import com.marvel.domain.model.Result

interface CharactersRepository {
    suspend fun getCharacters(size: Int, skip: Int) : Result<List<Character>>
    suspend fun getCharacterById(id: Int): Result<Character>
}