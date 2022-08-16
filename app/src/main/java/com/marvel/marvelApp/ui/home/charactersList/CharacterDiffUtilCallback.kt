package com.marvel.marvelApp.ui.home.charactersList

import androidx.recyclerview.widget.DiffUtil
import com.marvel.domain.model.Character

object CharacterDiffUtilCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem
}