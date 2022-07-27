package com.example.testapp.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.App
import com.example.testapp.R
import com.example.testapp.databinding.FragmentCharactersBinding
import kotlinx.coroutines.flow.collectLatest

const val PAGE_SIZE = 25

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = CharactersFragment()
    }

    private lateinit var viewModel: CharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CharactersViewModel::class.java)
        (activity?.application as App).getComponent().inject(viewModel)

        val adapter = CharacterListAdapter(requireContext())

        initList(adapter)
        collectViewModelData(adapter)

        viewModel.fetchCharacters(PAGE_SIZE)
    }

    private fun initList(adapter: CharacterListAdapter) {
        adapter.setClickListener(object : CharacterListAdapter.ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                findNavController().navigate(R.id.action_CharactersFragment_to_CharacterDetailFragment)
                println(adapter.currentList[position].id)
            }
        })

        val listLayoutManager = LinearLayoutManager(requireContext())
        binding.rvHome.layoutManager = listLayoutManager
        binding.rvHome.adapter = adapter
        binding.rvHome.addOnScrollListener(
            CharactersListScrollListener(
                listLayoutManager,
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

