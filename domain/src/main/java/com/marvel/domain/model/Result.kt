package com.marvel.domain.model

import com.marvel.domain.model.errors.ErrorEntity

sealed class Result<T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error<T>(val error: ErrorEntity) : Result<T>()
}