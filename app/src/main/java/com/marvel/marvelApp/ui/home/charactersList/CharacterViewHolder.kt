package com.marvel.marvelApp.ui.home.charactersList

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.marvel.domain.model.Character
import com.marvel.marvelApp.extensions.loadUrl

interface ItemClickListener {
    fun onItemClick(position: Int)
}

class CharacterViewHolder(private val binding: ItemCharacterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    lateinit var itemClickListener: ItemClickListener

    fun bind(character: Character) {
        binding.character = character
        binding.ivCharacterThumbnail.loadUrl(character.thumbNail)
    }

    init {
        binding.root.setOnClickListener {
            itemClickListener.onItemClick(adapterPosition)
        }
    }
}