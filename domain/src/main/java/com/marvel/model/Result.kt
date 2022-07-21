package com.marvel.model

import com.marvel.model.errors.DomainError

data class Result<T>(val result: T?, val error: DomainError? = null)