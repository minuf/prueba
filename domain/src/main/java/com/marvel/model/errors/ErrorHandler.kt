package com.marvel.model.errors

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}