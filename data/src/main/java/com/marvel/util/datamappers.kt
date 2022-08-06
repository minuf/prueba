package com.marvel.util

import com.marvel.local.room.DbCharacterModel
import com.marvel.remote.retrofit.data.RemoteCharacterModel
import com.marvel.domain.model.Character

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