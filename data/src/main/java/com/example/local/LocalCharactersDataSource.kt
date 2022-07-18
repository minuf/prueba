package com.example.local

import androidx.room.Room
import com.example.local.room.CharacterDb
import com.marvel.entities.Character
import com.marvel.entities.Image

class LocalCharactersDataSource(
    private val room: CharacterDb = Room.databaseBuilder(context, CharacterDb::class.java, "character").build()
) {
    val characters = arrayListOf<Character>()

    suspend fun getCharacters(size: Int, skip: Int): List<Character> {
        val result = room.characterDao().getCharacters(size, skip)
        return characters
    }

    suspend fun getCharacterById(id: Int): Character {
        return Character(5, "", "", Image("", ""))
    }

    suspend fun saveCharacters(characters: List<Character>) {
        this.characters += characters
    }
}