package com.example.testapp.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testapp.core.domain.model.Character
import com.example.testapp.core.domain.ports.GetCharacters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 50
private const val FIRST_NUMBER_OF_PAGES_LOADED = 3

class CharactersViewModel : ViewModel() {

    @Inject
    lateinit var getCharacters: GetCharacters

    private val _characters = MutableStateFlow<List<Character>>(listOf())

    var characters: Flow<PagingData<Character>> = flowOf()

    fun fetchCharacters() {
        viewModelScope.launch {
            characters = getPaginatedFlowOfCharacters()
                .cachedIn(viewModelScope)
        }
    }

    private fun getPaginatedFlowOfCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = PAGE_SIZE * FIRST_NUMBER_OF_PAGES_LOADED,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharacterPagingSource(getCharacters)
            }
        ).flow
    }
}