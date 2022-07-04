package com.example.testapp.ui.characters

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.App
import com.example.testapp.databinding.FragmentCharactersBinding
import kotlinx.coroutines.flow.collectLatest

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
        //viewModel = CharactersViewModel(lifecycleScope)
        (activity?.application as App).getComponent().inject(viewModel)
        // TODO: Use the ViewModel
        viewModel.fetchCharacters()
        val adapter = CharactersAdapter(requireContext())
        /*adapter.setClickListener(object : CharacterListAdapter.ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                println(adapter.currentList[position].name)
            }
        })*/

        //binding.rvHome.layoutManager = GridLayoutManager(context, 3)
        binding.rvHome.layoutManager = LinearLayoutManager(context)
        binding.rvHome.adapter = adapter

        binding.srHome.setOnRefreshListener {
            viewModel.fetchCharacters()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.paginatedCharactersFlow.collectLatest {
                adapter.submitData(it)
                binding.srHome.isRefreshing = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

