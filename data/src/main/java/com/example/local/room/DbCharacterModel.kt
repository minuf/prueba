package com.example.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbCharacterModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String,
    val thumbNail: String
)