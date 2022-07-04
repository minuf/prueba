package com.example.testapp.core.domain.ports

import com.example.testapp.core.domain.model.Character
import com.example.testapp.core.domain.repository.CharactersRepository

class GetCharacters(private val charactersRepository: CharactersRepository) {
    suspend fun run(total: Int = 50, skip: Int = 0): List<Character> {
        return charactersRepository.getCharacters(total, skip)
    }
}