package com.marvel.marvelApp.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.google.android.material.snackbar.Snackbar
import com.marvel.marvelApp.ui.BaseFragment
import com.marvel.marvelApp.ui.home.charactersList.CharacterListAdapter
import com.marvel.marvelApp.ui.home.charactersList.CharactersListScrollListener
import com.marvel.marvelApp.ui.home.charactersList.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private val viewModel: CharactersViewModel by viewModels()

    private lateinit var mLayoutManager: LinearLayoutManager

    private lateinit var binding: FragmentCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        //binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, null, false)
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

        mLayoutManager = LinearLayoutManager(requireContext())
        binding.rvHome.layoutManager = mLayoutManager
        binding.rvHome.adapter = adapter
        binding.rvHome.addOnScrollListener(
            CharactersListScrollListener(
                mLayoutManager,
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

