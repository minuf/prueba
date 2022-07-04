package com.example.testapp.ui.characters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testapp.core.domain.model.Character
import com.example.testapp.core.domain.ports.GetCharacters

class CharacterPagingSource(private val getCharacters: GetCharacters) :
    PagingSource<Int, Character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val position = params.key ?: 1
            val characters = getCharacters.run(skip = params.loadSize * position)
            return LoadResult.Page(
                data = characters,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}