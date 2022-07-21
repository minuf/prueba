package com.example.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DbCharacterModel::class],
    version = 1
)
abstract class CharacterDb : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}