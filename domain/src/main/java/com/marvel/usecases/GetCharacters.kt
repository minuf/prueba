package com.marvel.usecases

import com.marvel.entities.Character
import com.marvel.repositories.CharactersRepository

class GetCharacters(private val charactersRepository: CharactersRepository) {
    suspend fun run(total: Int = 50, skip: Int = 0): List<Character> {
        return charactersRepository.getCharacters(total, skip)
    }
}