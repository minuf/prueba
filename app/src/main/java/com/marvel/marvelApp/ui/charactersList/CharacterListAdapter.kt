package com.marvel.marvelApp.ui.charactersList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.marvel.domain.model.Character
import com.marvel.marvelApp.extensions.loadUrl

class CharacterListAdapter internal constructor(context: Context) :
    ListAdapter<Character, CharacterListAdapter.CharacterViewHolder>(DiffUtilCallback) {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view: View = mInflater.inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvCharacterName.text = item.name
        holder.ivCharacterThumbnail.loadUrl(item.thumbNail)
    }

    inner class CharacterViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var tvCharacterName: TextView = itemView.findViewById(R.id.tv_character_name)
        var ivCharacterThumbnail: ImageView = itemView.findViewById(R.id.iv_character_thumbnail)

        override fun onClick(view: View?) {
            mClickListener?.onItemClick(view, adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem

}