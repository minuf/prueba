package com.marvel.usecases

import com.marvel.model.Character
import com.marvel.repositories.CharactersRepository

class GetCharacters(private val charactersRepository: CharactersRepository) {
    suspend fun run(total: Int = 5, skip: Int = 0): List<Character> {
        return charactersRepository.getCharacters(total, skip)
    }
}