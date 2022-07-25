package com.marvel.usecases

import com.marvel.model.Character
import com.marvel.model.Result
import com.marvel.repositories.CharactersRepository

class GetCharacters(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke(size: Int, skip: Int): Result<List<Character>> {
        return charactersRepository.getCharacters(size, skip)
    }
}