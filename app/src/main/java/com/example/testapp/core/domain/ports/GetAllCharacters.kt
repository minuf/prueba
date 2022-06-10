package com.example.marvelapplication.core.domain.ports

import com.example.marvelapplication.core.domain.model.Character
import com.example.marvelapplication.core.domain.repository.CharactersRepository

class GetAllCharacters(private val charactersRepository: CharactersRepository) {
    suspend fun run(): List<Character> {
        return charactersRepository.getAllCharacters()
    }
}