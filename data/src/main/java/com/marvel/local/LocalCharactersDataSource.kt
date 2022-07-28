package com.marvel.local

import com.marvel.local.room.CharacterDao
import com.marvel.model.Character
import com.marvel.util.toDbModel
import com.marvel.util.toDomainModel

interface LocalCharactersDataSource {
    suspend fun getCharacters(size: Int, skip: Int): List<Character>
    suspend fun getCharacterById(id: Int): Character
    suspend fun saveCharacters(characters: List<Character>)
}

class LocalCharactersDataSourceImpl(
    private val characterDao: CharacterDao
) : LocalCharactersDataSource {
    val characters = arrayListOf<Character>()

    override suspend fun getCharacters(size: Int, skip: Int): List<Character> {
        //val result = room.characterDao().getCharacters(size, skip)
        val result = characterDao.getCharacters(size, skip)
            .map { it.toDomainModel() }
        println(result)
        return result
    }

    override suspend fun getCharacterById(id: Int): Character {
        return Character(5, "", "", "")
    }

    override suspend fun saveCharacters(characters: List<Character>) {
        characterDao.insertCharacters(
            characters.map { it.toDbModel() }
        )
    }
}