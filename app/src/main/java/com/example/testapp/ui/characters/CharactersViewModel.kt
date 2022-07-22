package com.example.testapp.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.model.Character
import com.marvel.model.Result
import com.marvel.model.errors.ErrorEntity
import com.marvel.usecases.GetCharacters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PAGE_SIZE = 50

class CharactersViewModel : ViewModel() {

    @Inject
    lateinit var getCharacters: GetCharacters

    private var isLoading = false

    private val _characters = MutableStateFlow<List<Character>>(listOf())

    val characters: StateFlow<List<Character>> = _characters

    fun fetchCharacters(total: Int = PAGE_SIZE, skip: Int = 0) {
        if (!isLoading) {
            isLoading = true
            viewModelScope.launch(Dispatchers.IO) {
                getCharacters(total, skip).let { result ->
                    if (result is Result.Success) {
                        _characters.value += result.data
                    }
                    if (result is Result.Error) {
                        if (result.error is ErrorEntity.Network)
                        println("Error from viewmodel" + result.error)
                    }
                    isLoading = false
                }
            }
        }
    }
}