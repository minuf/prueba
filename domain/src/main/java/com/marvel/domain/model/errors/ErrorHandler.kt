package com.marvel.domain.model.errors

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}