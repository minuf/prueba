package com.example.testapp.ui.characters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.squareup.picasso.Picasso
import com.marvel.entities.Character

class CharacterListAdapter internal constructor(context: Context) :
    ListAdapter<Character, CharacterListAdapter.ViewHolder>(DiffUtilCallback) {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvCharacterName.text = item.name

        val imageUrl = item.thumbnail.path.replace("http", "https") +
                "/portrait_xlarge." +
                item.thumbnail.extension

        Picasso.get()
            .load(imageUrl)
            .resize(400, 400)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(holder.ivCharacterThumbnail)
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var tvCharacterName: TextView = itemView.findViewById(R.id.tv_character_name)
        var ivCharacterThumbnail: ImageView = itemView.findViewById(R.id.iv_character_thumbnail)

        override fun onClick(view: View?) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
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