package com.marvel.marvelApp.ui.home.charactersList

import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.marvel.domain.model.Character

class CharacterViewHolder(private val binding: ItemCharacterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(character: Character) {
        binding.character = character
    }
}