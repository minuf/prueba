package com.example.marvelapplication.core.domain.ports

import com.example.marvelapplication.core.domain.model.Character
import com.example.marvelapplication.core.domain.repository.CharactersRepository

class GetCharacterById(private val charactersRepository: CharactersRepository) {
    suspend fun run(id: Int): Character {
        return charactersRepository.getCharacterById(id)
    }
}