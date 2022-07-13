package com.marvel.usecases

import com.marvel.entities.Character
import com.marvel.repository.CharactersRepository

class GetCharacterById(private val charactersRepository: CharactersRepository) {
    suspend fun run(id: Int): Character {
        return charactersRepository.getCharacterById(id)
    }
}