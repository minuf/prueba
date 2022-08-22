package com.marvel.marvelApp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.marvel.domain.model.Character
import com.marvel.marvelApp.ui.BaseFragment
import com.marvel.marvelApp.ui.home.charactersList.CharacterItemClickListener
import com.marvel.marvelApp.ui.home.charactersList.CharacterListAdapter
import com.marvel.marvelApp.ui.home.charactersList.CharactersListScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {

    private val viewModel: CharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CharacterListAdapter()

        initList(adapter)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initList(adapter: CharacterListAdapter) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharacters.layoutManager = layoutManager
        binding.rvCharacters.adapter = adapter

        binding.rvCharacters.addOnScrollListener(
            CharactersListScrollListener(
                layoutManager,
                viewModel
            )
        )

        adapter.setClickListener(object : CharacterItemClickListener {
            override fun onClick(character: Character) {
                navigateToCharacterDetail(character)
            }
        })
    }

    private fun navigateToCharacterDetail(character: Character) {
        val action =
            CharactersFragmentDirections
                .actionCharactersFragmentToCharacterDetailFragment(
                    character
                )
        findNavController().navigate(action)
    }
}

