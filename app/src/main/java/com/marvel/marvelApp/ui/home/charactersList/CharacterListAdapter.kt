package com.marvel.marvelApp.ui.home.charactersList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.marvel.domain.model.Character

class CharacterListAdapter() :
    ListAdapter<Character, CharacterViewHolder>(CharacterDiffUtilCallback) {

    private lateinit var binding: ItemCharacterBinding
    private lateinit var mClickListener: CharacterItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        binding.root.setOnClickListener {
            mClickListener.onClick(item)
        }
    }

    fun setClickListener(characterItemClickListener: CharacterItemClickListener) {
        mClickListener = characterItemClickListener
    }
}