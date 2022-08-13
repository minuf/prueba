package com.marvel.marvelApp.ui.characterDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharacterDetailBinding
import com.marvel.domain.model.Character
import com.marvel.marvelApp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding>() {

    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val character = arguments?.get("character") as Character

        binding.tvCharacterDetailName.text = character.name
        Glide.with(this@CharacterDetailFragment)
            .load(character.thumbNail)
            .apply(RequestOptions().override(600, 400))
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivCharacterDetailThumbnail)
    }
}