package com.example.testapp

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapplication.core.domain.model.Character
import com.squareup.picasso.Picasso

class CharacterListAdapter internal constructor(private val context: Context, data: List<Character>) :
    RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {
    var mData: List<Character> = data
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mData[position]
        holder.tvCharacterName.text = item.name

        val imageUrl = item.thumbnail.path.replace("http", "https") +
                "/portrait_xlarge." +
                item.thumbnail.extension

        val x = context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK
        Configuration.SCREENLAYOUT_SIZE_LARGE
        println("SIZE $x")

        Picasso.get()
            .load(imageUrl)
            .resize(400, 400)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(holder.ivCharacterThumbnail)
    }

    override fun getItemCount(): Int {
        return mData.size
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