package com.marvel.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM Character LIMIT :size OFFSET :skip")
    suspend fun getCharacters(size: Int, skip: Int): List<DbCharacterModel>

    @Query("SELECT * FROM Character WHERE id = :id")
    suspend fun getCharacterById(id: Int): DbCharacterModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<DbCharacterModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: DbCharacterModel)
}