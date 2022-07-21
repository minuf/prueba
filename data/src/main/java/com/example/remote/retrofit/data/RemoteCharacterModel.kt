package com.example.remote.retrofit.data

data class RemoteCharacterModel(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Image
)

data class Image(val path: String, val extension: String)