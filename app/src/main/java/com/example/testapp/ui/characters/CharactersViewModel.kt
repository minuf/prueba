package com.example.testapp.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.testapp.core.domain.model.Character
import com.example.testapp.core.domain.ports.GetAllCharacters
import com.example.testapp.core.domain.repository.CharactersRepository
import com.example.testapp.core.infra.adapter.retrofit.RetrofitCharactersRepository
import com.example.testapp.core.infra.adapter.retrofit.services.CharactersService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersViewModel(): ViewModel() {
    @Inject
    lateinit var getAllCharacters: GetAllCharacters

    @Inject
    lateinit var charactersService: CharactersService

    //var characters = RetrofitCharactersRepository(charactersService).getAllCharactersAsFlow().cachedIn(viewModelScope)

    private val _characters = MutableStateFlow<List<Character>>(listOf())

    var characters: Flow<PagingData<Character>> = flowOf()

    fun fetchCharacters() {
        viewModelScope.launch {
            /*_characters.value = withContext(Dispatchers.IO) {
                getAllCharacters.run()
            }*/

            characters = RetrofitCharactersRepository(charactersService).getAllCharactersAsFlow()
                .cachedIn(viewModelScope)
        }
    }
}