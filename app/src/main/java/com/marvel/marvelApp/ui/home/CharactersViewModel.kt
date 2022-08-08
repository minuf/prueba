package com.marvel.marvelApp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.marvel.domain.model.Character
import com.marvel.model.Result
import com.marvel.model.errors.ErrorEntity
import com.marvel.usecases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    private var isLoading = false

    private val _characters = MutableStateFlow<List<Character>>(listOf())
    private val _isNetworkReachable = MutableStateFlow(true)

    val characters: StateFlow<List<Character>> = _characters
    val isNetworkReachable: StateFlow<Boolean> = _isNetworkReachable

    init {
        fetchCharacters(25)
    }

    fun fetchCharacters(total: Int, skip: Int = 0) {
        if (!isLoading) {
            isLoading = true
            viewModelScope.launch(Dispatchers.IO) {
                getCharactersUseCase(total, skip).let { result ->
                    if (result is Result.Success) {
                        _isNetworkReachable.value = true
                        _characters.value += result.data
                    } else if (result is Result.Error) {
                        handleError(result.error)
                    }
                    isLoading = false
                }
            }
        }
    }

    private fun handleError(error: ErrorEntity) {
        if (error is ErrorEntity.Network) {
            _isNetworkReachable.value = false
        } else {
            println("VIEWMODEL: UNEXPECTED ERROR")
        }
    }
}