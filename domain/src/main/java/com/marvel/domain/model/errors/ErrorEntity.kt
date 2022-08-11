package com.marvel.domain.model.errors

sealed class ErrorEntity {

    //TODO: ADD-REMOVE USED ERRORS

    object Network : ErrorEntity()

    object NotFound : ErrorEntity()

    object AccessDenied : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()

    object Unknown : ErrorEntity()
}