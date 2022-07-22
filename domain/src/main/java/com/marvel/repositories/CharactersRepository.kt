package com.marvel.repositories

import com.marvel.model.Character
import com.marvel.model.Result

interface CharactersRepository {
    suspend fun getCharacters(size: Int, skip: Int) : Result<List<Character>>
    suspend fun getCharacterById(id: Int): Character
}