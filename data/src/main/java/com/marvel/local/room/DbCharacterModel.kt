package com.marvel.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Character")
data class DbCharacterModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String,
    val thumbNail: String
)