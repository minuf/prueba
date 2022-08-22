package com.marvel.marvelApp.ui.home.charactersList

import com.marvel.domain.model.Character

interface CharacterItemClickListener {
    fun onClick(character: Character)
}