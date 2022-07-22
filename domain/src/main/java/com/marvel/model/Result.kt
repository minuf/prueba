package com.marvel.model

import com.marvel.model.errors.ErrorEntity

sealed class Result<T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error<T>(val error: ErrorEntity) : Result<T>()
}