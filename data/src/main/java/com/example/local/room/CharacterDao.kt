package com.example.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM Character LIMIT :size OFFSET :skip")
    suspend fun getCharacters(size: Int, skip: Int): List<Character>

    @Query("SELECT * FROM Character WHERE id = :id")
    suspend fun getCharacterById(id: Int): Character

    @Insert
    suspend fun insertCharacters(characters: List<Character>)

    @Insert
    suspend fun insertCharacter(character: Character)
}