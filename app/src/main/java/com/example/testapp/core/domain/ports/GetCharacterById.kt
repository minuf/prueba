package com.example.testapp.core.domain.ports

import com.example.domain.Character
import com.example.testapp.core.domain.repository.CharactersRepository

class GetCharacterById(private val charactersRepository: CharactersRepository) {
    suspend fun run(id: Int): Character {
        return charactersRepository.getCharacterById(id)
    }
}