package com.marvel.usecases

import com.marvel.model.Character
import com.marvel.model.Result
import com.marvel.repositories.CharactersRepository

interface GetCharacterByIdUseCase {
    suspend operator fun invoke(id: Int): Result<Character>
}

class GetCharacterByIdUseCaseImpl(private val charactersRepository: CharactersRepository) :
    GetCharacterByIdUseCase {
    override suspend operator fun invoke(id: Int): Result<Character> {
        return charactersRepository.getCharacterById(id)
    }
}