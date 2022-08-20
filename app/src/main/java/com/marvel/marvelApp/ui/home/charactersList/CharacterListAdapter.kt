package com.marvel.marvelApp.ui.home.charactersList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.marvel.domain.model.Character
import com.marvel.marvelApp.extensions.loadUrl

class CharacterListAdapter(context: Context) :
    ListAdapter<Character, CharacterViewHolder>(CharacterDiffUtilCallback) {

    //private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var mClickListener: ItemClickListener

    private lateinit var binding: ItemCharacterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemClickListener = mClickListener
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        mClickListener = itemClickListener
    }
}