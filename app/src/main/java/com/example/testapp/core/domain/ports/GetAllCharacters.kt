package com.example.testapp.core.domain.ports

import com.example.testapp.core.domain.model.Character
import com.example.testapp.core.domain.repository.CharactersRepository

class GetAllCharacters(private val charactersRepository: CharactersRepository) {
    suspend fun run(): List<Character> {
        return charactersRepository.getAllCharacters()
    }
}