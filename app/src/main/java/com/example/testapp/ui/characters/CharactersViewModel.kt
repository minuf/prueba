package com.example.testapp.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.testapp.core.domain.model.Character
import com.example.testapp.core.domain.ports.GetCharacters
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 50
private const val FIRST_NUMBER_OF_PAGES_LOADED = 3

class CharactersViewModel: ViewModel() {
    @Inject
    lateinit var getCharacters: GetCharacters

    //var characters = RetrofitCharactersRepository(charactersService).getAllCharactersAsFlow().cachedIn(viewModelScope)

    private val _characters = MutableStateFlow<List<Character>>(listOf())

    var paginatedCharactersFlow: Flow<PagingData<Character>> = flowOf()

    fun fetchCharacters() {
        viewModelScope.launch {
            /*_characters.value = withContext(Dispatchers.IO) {
                getAllCharacters.run()
            }*/

            paginatedCharactersFlow = getPaginatedFlowOfCharacters()
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