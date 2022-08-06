package com.marvel.usecases

import com.marvel.domain.model.Character
import com.marvel.model.Result
import com.marvel.repositories.CharactersRepository

interface GetCharactersUseCase {
    suspend operator fun invoke(size: Int, skip: Int): Result<List<Character>>
}

class GetCharactersUseCaseImpl(private val charactersRepository: CharactersRepository) :
    GetCharactersUseCase {
    override suspend operator fun invoke(size: Int, skip: Int): Result<List<Character>> {
        return charactersRepository.getCharacters(size, skip)
    }
}