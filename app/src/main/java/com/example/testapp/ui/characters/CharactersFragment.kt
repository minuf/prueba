package com.example.testapp.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.App
import com.example.testapp.databinding.FragmentCharactersBinding
import kotlinx.coroutines.flow.collectLatest

const val PAGE_SIZE = 25

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private lateinit var mLayoutManager: LinearLayoutManager

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
        adapter.setClickListener(object : CharacterListAdapter.ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                //TODO: NAVIGATE TO CHARACTER DETAIL SCREEN
                println(adapter.currentList[position].id)
            }
        })

        //binding.rvHome.layoutManager = GridLayoutManager(context, 3)
        mLayoutManager = LinearLayoutManager(requireContext())
        binding.rvHome.layoutManager = mLayoutManager
        binding.rvHome.adapter = adapter

        viewModel.fetchCharacters(PAGE_SIZE)

        lifecycleScope.launchWhenCreated {
            viewModel.characters.collectLatest {
                adapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.isInternetReachable.collectLatest {
                if (it) {
                    println("INTERNET REACHABLE")
                } else {

                    println("INTERNET REACHABLE NOT")
                }
            }
        }


        //TODO: REFACTOR SCROLL LISTENER TO NEW CLASS
        binding.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var lastItemCount = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (isScrollingDown(dy)) {
                    if (shouldCall()) {
                        doCharactersCall()
                        println("SHOULD FETCH")
                        return
                    }
                    println("SHOULD FETCH NOT")
                }
            }

            private fun doCharactersCall() {
                val totalItemCount: Int = mLayoutManager.itemCount
                viewModel.fetchCharacters(PAGE_SIZE, totalItemCount)
                lastItemCount = totalItemCount
            }

            private fun shouldCall(): Boolean {
                return isEnd() && lastItemCount != mLayoutManager.itemCount
            }

            private fun isEnd(): Boolean {
                val visibleItemCount = mLayoutManager.childCount
                val totalItemCount = mLayoutManager.itemCount
                val pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()

                return visibleItemCount + pastVisiblesItems >= totalItemCount - 10
            }

            private fun isScrollingDown(y: Int): Boolean {
                return y > 0
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

