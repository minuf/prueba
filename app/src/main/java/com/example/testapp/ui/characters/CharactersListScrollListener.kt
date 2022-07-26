package com.example.testapp.ui.characters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CharactersListScrollListener(
    private val mLayoutManager: LinearLayoutManager,
    private val viewModel: CharactersViewModel
) : RecyclerView.OnScrollListener() {
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
        val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()

        return visibleItemCount + pastVisibleItems >= totalItemCount - 10
    }

    private fun isScrollingDown(y: Int): Boolean {
        return y > 0
    }
}