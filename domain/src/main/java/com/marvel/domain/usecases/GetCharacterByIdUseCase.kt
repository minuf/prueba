package com.marvel.domain.usecases

import com.marvel.domain.model.Character
import com.marvel.domain.model.Result
import com.marvel.domain.repositories.CharactersRepository

interface GetCharacterByIdUseCase {
    suspend operator fun invoke(id: Int): Result<Character>
}

class GetCharacterByIdUseCaseImpl(private val charactersRepository: CharactersRepository) :
    GetCharacterByIdUseCase {
    override suspend operator fun invoke(id: Int): Result<Character> {
        return charactersRepository.getCharacterById(id)
    }
}