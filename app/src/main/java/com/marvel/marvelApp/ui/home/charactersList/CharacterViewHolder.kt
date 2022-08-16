package com.marvel.marvelApp.ui.home.charactersList

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R

interface ItemClickListener {
    fun onItemClick(position: Int)
}

class CharacterViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    var tvCharacterName: TextView = itemView.findViewById(R.id.tv_character_name)
    var ivCharacterThumbnail: ImageView = itemView.findViewById(R.id.iv_character_thumbnail)
    lateinit var itemClickListener: ItemClickListener

    init {
        itemView.setOnClickListener {
            itemClickListener.onItemClick(adapterPosition)
        }
    }
}