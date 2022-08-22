package com.marvel.marvelApp.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.google.android.material.snackbar.Snackbar
import com.marvel.marvelApp.ui.BaseFragment
import com.marvel.marvelApp.ui.home.charactersList.CharacterListAdapter
import com.marvel.marvelApp.ui.home.charactersList.CharactersListScrollListener
import com.marvel.marvelApp.ui.home.charactersList.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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

        val adapter = CharacterListAdapter(requireContext())

        initList(adapter)
        collectViewModelData(adapter)
    }

    private fun initList(adapter: CharacterListAdapter) {
        adapter.setClickListener(object : ItemClickListener {
            override fun onItemClick(position: Int) {
                val character = adapter.currentList[position]
                val action =
                    CharactersFragmentDirections
                        .actionCharactersFragmentToCharacterDetailFragment(
                            character
                        )
                findNavController().navigate(action)
            }
        })

        val man = LinearLayoutManager(requireContext())
        binding.rvCharacters.layoutManager = man
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.addOnScrollListener(
            CharactersListScrollListener(
                man,
                viewModel
            )
        )
    }

    private fun collectViewModelData(adapter: CharacterListAdapter) {
        lifecycleScope.launchWhenCreated {
            viewModel.characters.collectLatest {
                adapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.isNetworkReachable.collectLatest { internetReachable ->
                if (!internetReachable) {
                    showErrorMessageAndRetry(adapter.currentList.size)
                }
            }
        }
    }

    private fun showErrorMessageAndRetry(skip: Int) {
        Snackbar.make(
            binding.root,
            "Error",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("RETRY") {
                viewModel.fetchCharacters(PAGE_SIZE, skip)
            }
            .setBackgroundTint(
                Color.RED
            )
            .show()
    }
}

