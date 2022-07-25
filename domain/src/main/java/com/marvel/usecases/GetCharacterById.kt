package com.marvel.usecases

import com.marvel.model.Character
import com.marvel.model.Result
import com.marvel.repositories.CharactersRepository

class GetCharacterById(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke(id: Int): Result<Character> {
        return charactersRepository.getCharacterById(id)
    }
}