package com.example

import com.example.local.LocalCharactersDataSource
import com.example.remote.RemoteCharactersDataSource
import com.marvel.model.Character
import com.marvel.model.Result
import com.marvel.model.errors.ErrorHandler
import com.marvel.repositories.CharactersRepository

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

    override suspend fun getCharacterById(id: Int): Character {
        return remoteDataSource.getCharacterById(id)
    }
}