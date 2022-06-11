package com.example.testapp.ui.characters

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testapp.databinding.FragmentCharactersBinding

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
        // TODO: Use the ViewModel
        val adapter = CharacterListAdapter(requireContext(), listOf())
        adapter.setClickListener(object : CharacterListAdapter.ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                println(adapter.currentList[position].name)
            }
        })

        binding.rvHome.layoutManager = GridLayoutManager(context, 3)
        binding.rvHome.adapter = adapter

        binding.srHome.setOnRefreshListener {
            viewModel.fetchCharacters()
        }

        viewModel.characters.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.srHome.isRefreshing = false
            println(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

