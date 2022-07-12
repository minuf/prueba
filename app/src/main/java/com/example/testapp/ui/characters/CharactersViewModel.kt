package com.example.testapp.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Character
import com.example.testapp.core.domain.ports.GetCharacters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel : ViewModel() {

    @Inject
    lateinit var getCharacters: GetCharacters

    private var isLoading = false

    private val _characters = MutableStateFlow<List<Character>>(listOf())

    val characters: StateFlow<List<Character>> = _characters


    fun fetchCharacters(total: Int = 50, skip: Int = 0) {
        if (!isLoading) {
            isLoading = true
            viewModelScope.launch {
                _characters.value += getCharacters.run(total, skip)
                isLoading = false
            }
        }
    }
}