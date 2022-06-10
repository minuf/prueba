package com.example.marvelapplication.core.domain.repository

import com.example.marvelapplication.core.domain.model.Character

interface CharactersRepository {
    suspend fun getAllCharacters() : List<Character>
    suspend fun getCharacterById(id: Int): Character
}