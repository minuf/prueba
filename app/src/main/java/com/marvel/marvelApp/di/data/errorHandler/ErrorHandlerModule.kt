package com.marvel.marvelApp.di.data.errorHandler

import com.marvel.GeneralErrorHandlerImpl
import com.marvel.domain.model.errors.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ErrorHandlerModule {

    @Provides
    fun providesGeneralErrorHandler(): ErrorHandler {
        return GeneralErrorHandlerImpl()
    }
}