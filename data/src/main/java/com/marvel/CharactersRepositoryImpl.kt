package com.marvel

import com.marvel.local.LocalCharactersDataSource
import com.marvel.remote.RemoteCharactersDataSource
import com.marvel.domain.model.Character
import com.marvel.model.Result
import com.marvel.model.errors.ErrorHandler
import com.marvel.repositories.CharactersRepository
import kotlinx.coroutines.*

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
                saveCharactersAsync(characters)
            }
            Result.Success(characters)

        } catch (t: Throwable) {
            Result.Error(errorHandler.getError(t))
        }
    }

    override suspend fun getCharacterById(id: Int): Result<Character> {
        return try {
            Result.Success(remoteDataSource.getCharacterById(id))
        } catch (t: Throwable) {
            Result.Error(errorHandler.getError(t))
        }
    }

    private fun saveCharactersAsync(characters: List<Character>) {
        //TODO: USE OTHER SCOPE
        GlobalScope.launch(Dispatchers.IO) {
            localDataSource.saveCharacters(characters)
        }
    }
}