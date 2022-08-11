package com.marvel

import com.marvel.domain.model.Character
import com.marvel.domain.model.Result
import com.marvel.domain.model.errors.ErrorHandler
import com.marvel.domain.repositories.CharactersRepository
import com.marvel.local.LocalCharactersDataSource
import com.marvel.remote.RemoteCharactersDataSource

class CharactersRepositoryImpl(
    private val remoteDataSource: RemoteCharactersDataSource,
    private val localDataSource: LocalCharactersDataSource,
    private val errorHandler: ErrorHandler = GeneralErrorHandlerImpl()
) : CharactersRepository {

    override suspend fun getCharacters(size: Int, skip: Int): Result<List<Character>> {
        return try {
            var characters = localDataSource.getCharacters(size, skip)
            if (characters.isEmpty()) {
                characters = remoteDataSource.getCharacters(size, skip)
                localDataSource.saveCharacters(characters)
            }
            Result.Success(characters)

        } catch (t: Throwable) {
            Result.Error(errorHandler.getError(t))
        }
    }

    override suspend fun getCharacterById(id: Int): Result<Character> {
        return try {
            Result.Success(localDataSource.getCharacterById(id))
        } catch (t: Throwable) {
            Result.Error(errorHandler.getError(t))
        }
    }
}