package com.marvel.domain.usecases

import com.marvel.domain.model.Character
import com.marvel.domain.model.Result
import com.marvel.domain.repositories.CharactersRepository

interface GetCharactersUseCase {
    suspend operator fun invoke(size: Int, skip: Int): Result<List<Character>>
}

class GetCharactersUseCaseImpl(private val charactersRepository: CharactersRepository) :
    GetCharactersUseCase {
    override suspend operator fun invoke(size: Int, skip: Int): Result<List<Character>> {
        return charactersRepository.getCharacters(size, skip)
    }
}