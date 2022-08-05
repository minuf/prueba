package com.marvel.marvelApp.ui.charactersList

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvel.marvelApp.ui.home.CharactersViewModel
import com.marvel.marvelApp.ui.home.PAGE_SIZE

class CharactersListScrollListener(
    private val mLayoutManager: LinearLayoutManager,
    private val viewModel: CharactersViewModel
) : RecyclerView.OnScrollListener() {

    private var lastItemCount = 0

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
        return isEnd() && !isRepeatedCall()
    }

    private fun isRepeatedCall() : Boolean {
        return lastItemCount == mLayoutManager.itemCount
    }

    private fun isEnd(): Boolean {
        val visibleItemCount = mLayoutManager.childCount
        val totalItemCount = mLayoutManager.itemCount
        val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
        //val pastVisibleItems = mLayoutManager.findLastVisibleItemPosition()

        return visibleItemCount + pastVisibleItems >= totalItemCount - 10
    }

    private fun isScrollingDown(y: Int): Boolean {
        return y > 0
    }
}