package com.example.local

import com.example.local.room.CharacterDao
import com.example.local.room.CharacterDb
import com.example.util.toDbModel
import com.example.util.toDomainModel
import com.marvel.model.Character

class LocalCharactersDataSource(
    private val characterDao: CharacterDao
) {
    val characters = arrayListOf<Character>()

    suspend fun getCharacters(size: Int, skip: Int): List<Character> {
        //val result = room.characterDao().getCharacters(size, skip)
        val result = characterDao.getCharacters(size, skip)
            .map { it.toDomainModel() }
        println(result)
        return result
    }

    suspend fun getCharacterById(id: Int): Character {
        return Character(5, "", "", "")
    }

    suspend fun saveCharacters(characters: List<Character>) {
        characterDao.insertCharacters(
            characters.map { it.toDbModel() }
        )
    }
}