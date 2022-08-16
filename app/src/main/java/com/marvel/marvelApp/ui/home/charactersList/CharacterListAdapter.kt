package com.marvel.marvelApp.ui.home.charactersList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.marvelapp.R
import com.marvel.domain.model.Character
import com.marvel.marvelApp.extensions.loadUrl

class CharacterListAdapter(context: Context) :
    ListAdapter<Character, CharacterViewHolder>(CharacterDiffUtilCallback) {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var mClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view: View = mInflater.inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvCharacterName.text = item.name
        holder.ivCharacterThumbnail.loadUrl(item.thumbNail)
        holder.itemClickListener = mClickListener
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        mClickListener = itemClickListener
    }
}