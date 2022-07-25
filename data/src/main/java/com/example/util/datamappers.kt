package com.example.util

import com.example.local.room.DbCharacterModel
import com.example.remote.retrofit.data.RemoteCharacterModel
import com.marvel.model.Character

//TODO: REVIEW NEEDED CASTS
fun DbCharacterModel.toDomainModel() = Character(
    id, name, description, thumbNail
)

fun Character.toDbModel() = DbCharacterModel(
    id, name, description, thumbNail
)

fun RemoteCharacterModel.toDomainModel() = Character(
    id, name, description,
    thumbNail = thumbnail.path.replace("http", "https") +
            "/portrait_xlarge." +
            thumbnail.extension
)