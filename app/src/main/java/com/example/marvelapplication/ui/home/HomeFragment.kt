package com.example.marvelapplication.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapplication.R
import com.example.marvelapplication.core.domain.model.Character
import com.example.marvelapplication.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        val adapter = CharacterListAdapter(context, listOf())

        binding.rvHome.layoutManager = GridLayoutManager(context, 3)
        binding.rvHome.adapter = adapter

        binding.srHome.setOnRefreshListener {
            homeViewModel.fetchCharacters()
        }

        homeViewModel.characters.observe(viewLifecycleOwner) {
            adapter.mData = it
            adapter.notifyDataSetChanged()
            binding.srHome.isRefreshing = false
            println(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class CharacterListAdapter internal constructor(context: Context?, data: List<Character>) :
    RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {
    var mData: List<Character> = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mData[position]
        holder.tvCharacterName.text = item.name
        Picasso.get().load(R.drawable.ic_launcher_background)
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