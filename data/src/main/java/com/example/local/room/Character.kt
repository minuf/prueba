package com.example.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val thumbNail: String
)