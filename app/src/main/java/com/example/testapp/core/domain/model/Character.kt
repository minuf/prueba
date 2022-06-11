package com.example.testapp.core.domain.model

data class Character(val id: Int, val name: String, val description: String, val thumbnail: Image)
data class Image(val path: String, val extension: String)