package com.example.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM Character LIMIT :size OFFSET :skip")
    fun getCharacters(size: Int, skip: Int): List<Character>

    @Query("SELECT * FROM Character WHERE id = :id")
    fun getCharacterById(id: Int): Character

    @Insert
    fun insertCharacters(characters: List<Character>)

    @Insert
    fun insertCharacter(character: Character)
}