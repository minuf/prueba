package com.example.testapp.core.domain.repository

import com.example.testapp.core.domain.model.Character

interface CharactersRepository {
    suspend fun getAllCharacters() : List<Character>
    suspend fun getCharacterById(id: Int): Character
}