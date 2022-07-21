package com.marvel.usecases

import com.marvel.model.Character
import com.marvel.model.errors.DomainError
import com.marvel.model.errors.ErrorCode
import com.marvel.model.Result
import com.marvel.repositories.CharactersRepository

class GetCharacters(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke(total: Int, skip: Int): Result<List<Character>> {
        return try {
            Result(charactersRepository.getCharacters(total, skip), null)
        } catch (ex: Throwable) {
            Result(null, DomainError(ErrorCode.INTERNET_UNREACHABLE, ""))
        }
    }
}
