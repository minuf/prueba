package com.example.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM DbCharacterModel LIMIT :size OFFSET :skip")
    fun getCharacters(size: Int, skip: Int): List<DbCharacterModel>

    @Query("SELECT * FROM DbCharacterModel WHERE id = :id")
    fun getCharacterById(id: Int): DbCharacterModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<DbCharacterModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: DbCharacterModel)
}