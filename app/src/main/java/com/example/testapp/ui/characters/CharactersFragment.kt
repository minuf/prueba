package com.example.testapp.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
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
        binding.rvHome.adapter = adapter

        adapter.addLoadStateListener { loadState ->
            binding.rvHome.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.pgCharactersList.isVisible = loadState.source.refresh is LoadState.Loading
            binding.btRetry.isVisible = loadState.source.refresh is LoadState.Error
            handleError(loadState)
        }

        binding.btRetry.setOnClickListener { adapter.retry() }


        lifecycleScope.launchWhenCreated {
            viewModel.characters.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            Toast.makeText(requireContext(), "${it.error}", Toast.LENGTH_LONG).show()
        }
    }

}

