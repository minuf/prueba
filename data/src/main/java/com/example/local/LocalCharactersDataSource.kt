package com.example.local

import com.marvel.entities.Character
import com.marvel.entities.Image

class LocalCharactersDataSource {
    val characters = arrayListOf<Character>()
    suspend fun getCharacters(size: Int, skip: Int): List<Character> {
        return characters
    }

    suspend fun getCharacterById(id: Int): Character {
        return Character(5, "", "", Image("", ""))
    }

    suspend fun saveCharacters(characters: List<Character>) {
        this.characters += characters
    }
}