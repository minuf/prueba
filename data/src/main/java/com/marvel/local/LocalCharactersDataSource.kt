package com.marvel.local

import com.marvel.local.room.CharacterDao
import com.marvel.local.room.CharacterDb
import com.marvel.util.toDbModel
import com.marvel.util.toDomainModel
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