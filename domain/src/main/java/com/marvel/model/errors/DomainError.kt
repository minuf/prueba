package com.marvel.model.errors

data class DomainError (
    val errorCode: ErrorCode,
    val msg: String
)