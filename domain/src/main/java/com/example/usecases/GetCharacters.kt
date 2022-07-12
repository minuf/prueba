package com.example.usecases

import com.example.entities.Character
import com.example.repository.CharactersRepository

class GetCharacters(private val charactersRepository: CharactersRepository) {
    suspend fun run(total: Int = 50, skip: Int = 0): List<Character> {
        return charactersRepository.getCharacters(total, skip)
    }
}