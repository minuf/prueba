package com.marvel.marvelApp.ui.characterDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharacterDetailBinding
import com.marvel.usecases.GetCharacterByIdUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    @Inject
    lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase

    private var _binding: FragmentCharacterDetailBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: CharacterDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("characterId")

        viewModel =
            ViewModelProvider(this, CharacterDetailViewModelFactory(getCharacterByIdUseCase)).get(
                CharacterDetailViewModel::class.java
            )

        lifecycleScope.launch {
            viewModel.character.collectLatest {
                binding.tvCharacterDetailName.text = it?.name
                Glide.with(this@CharacterDetailFragment)
                    .load(it?.thumbNail)
                    .apply(RequestOptions().override(600, 400))
                    .placeholder(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.ivCharacterDetailThumbnail)
            }
        }

        viewModel.fetchCharacter(id!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}