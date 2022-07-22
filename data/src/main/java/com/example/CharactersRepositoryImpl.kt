package com.example

import com.example.local.LocalCharactersDataSource
import com.example.remote.RemoteCharactersDataSource
import com.marvel.model.Character
import com.marvel.model.Result
import com.marvel.model.errors.ErrorHandler
import com.marvel.repositories.CharactersRepository
import java.net.UnknownHostException

class CharactersRepositoryImpl(
    private val remoteDataSource: RemoteCharactersDataSource,
    private val localDataSource: LocalCharactersDataSource,
    private val errorHandler: ErrorHandler = GeneralErrorHandlerImpl()
) : CharactersRepository {
    override suspend fun getCharacters(size: Int, skip: Int): Result<List<Character>> {
        try {
            var characters = localDataSource.getCharacters(size, skip)
            if (characters.isNotEmpty()) println("DATA FROM DATABASE SIZE: $size, SKIP: $skip")
            if (characters.isEmpty()) {
                println("DATA FROM REMOTE")
                characters = remoteDataSource.getCharacters(size, skip)
                localDataSource.saveCharacters(characters)
            }
            return Result.Success(characters)

        } catch (t: Throwable) {
            if (t is UnknownHostException) {
                println("NO INTERNET")
            }
            //no internet
            //409 api error
            //database error
            println("ERROR: " + t)
            return Result.Error(errorHandler.getError(t))
        }
    }

    override suspend fun getCharacterById(id: Int): Character {
        return remoteDataSource.getCharacterById(id)
    }
}