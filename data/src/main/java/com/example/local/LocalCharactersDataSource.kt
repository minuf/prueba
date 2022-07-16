package com.example.local

import com.marvel.entities.Character
import com.marvel.entities.Image

class LocalCharactersDataSource {
    suspend fun getCharacters(size: Int, skip: Int): List<Character> {
        return listOf()
    }

    suspend fun getCharacterById(id: Int): Character {
        return Character(5, "", "", Image("", ""))
    }
}