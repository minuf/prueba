package com.marvel.marvelApp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.marvel.marvelApp.ui.BaseFragment
import com.marvel.marvelApp.ui.charactersList.CharacterListAdapter
import com.marvel.marvelApp.ui.charactersList.CharactersListScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {

    private val viewModel: CharactersViewModel by viewModels()

    private lateinit var mLayoutManager: LinearLayoutManager

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
        adapter.setClickListener(object : CharacterListAdapter.ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val character = adapter.currentList[position]
                val action =
                    CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
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
            viewModel.isNetworkReachable.collectLatest {
                if (it) {
                    println("INTERNET REACHABLE")
                } else {
                    println("INTERNET REACHABLE NOT")
                }
            }
        }
    }
}

