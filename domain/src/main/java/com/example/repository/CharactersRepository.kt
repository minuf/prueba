package com.example.repository

import com.example.entities.Character

interface CharactersRepository {
    suspend fun getCharacters(size: Int, skip: Int) : List<Character>
    suspend fun getCharacterById(id: Int): Character
}