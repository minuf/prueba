package com.marvel

import com.marvel.domain.model.errors.ErrorEntity
import com.marvel.domain.model.errors.ErrorHandler
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.UnknownHostException

class GeneralErrorHandlerImpl : ErrorHandler {

    //TODO: REIMPLEMENT THIS FOR USED ERRORS
    override fun getError(throwable: Throwable): ErrorEntity {
        return when(throwable) {
            is IOException,
            is UnknownHostException -> ErrorEntity.Network
            is HttpException -> {
                when(throwable.code()) {
                    // no cache found in case of no network, thrown by retrofit -> treated as network error
                    //DataConstants.Network.HttpStatusCode.UNSATISFIABLE_REQUEST -> ErrorEntity.Network

                    // not found
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound

                    // access denied
                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied

                    // unavailable service
                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable

                    // all the others will be treated as unknown error
                    else -> ErrorEntity.Unknown
                }
            }
            else -> ErrorEntity.Unknown
        }
    }
}