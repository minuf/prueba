package com.marvel.marvelApp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.marvel.marvelApp.ui.charactersList.CharacterListAdapter
import com.marvel.marvelApp.ui.charactersList.CharactersListScrollListener
import com.marvel.usecases.GetCharactersUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

const val PAGE_SIZE = 25

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    @Inject
    lateinit var getCharactersUseCase: GetCharactersUseCase

    private var _binding: FragmentCharactersBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: CharactersViewModel

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
        //viewModel = ViewModelProvider(this).get(CharactersViewModel::class.java)

        viewModel =
            ViewModelProvider(this, CharactersViewModelFactory(getCharactersUseCase)).get(
                CharactersViewModel::class.java
            )

        val adapter = CharacterListAdapter(requireContext())

        initList(adapter)
        collectViewModelData(adapter)

        viewModel.fetchCharacters(PAGE_SIZE)
    }

    private fun initList(adapter: CharacterListAdapter) {
        adapter.setClickListener(object : CharacterListAdapter.ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                val characterId = adapter.currentList[position].id
                val action =
                    CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
                        characterId
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

        if (viewModel.listState != null) {
            mLayoutManager.onRestoreInstanceState(viewModel.listState)
            viewModel.listState = null
        }
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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.listState = mLayoutManager?.onSaveInstanceState()
        _binding = null
    }
}

