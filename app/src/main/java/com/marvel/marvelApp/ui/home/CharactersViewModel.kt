package com.marvel.marvelApp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.domain.model.Character
import com.marvel.domain.model.Result
import com.marvel.domain.model.errors.ErrorEntity
import com.marvel.domain.usecases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PAGE_SIZE = 25

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) :
    ViewModel() {

    private var isLoading = false

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    private val _isNetworkReachable = MutableSharedFlow<Boolean>()

    val characters: StateFlow<List<Character>> = _characters
    val isNetworkReachable: SharedFlow<Boolean> = _isNetworkReachable

    init {
        fetchCharacters(PAGE_SIZE)
    }

    fun fetchCharacters(total: Int, skip: Int = 0) {
        if (!isLoading) {
            isLoading = true
            viewModelScope.launch(Dispatchers.IO) {
                val result = getCharactersUseCase(total, skip)
                if (result is Result.Success) {
                    _isNetworkReachable.emit(true)
                    _characters.value += result.data
                } else if (result is Result.Error) {
                    handleError(result.error)
                }
                isLoading = false
            }
        }
    }

    private suspend fun handleError(error: ErrorEntity) {
        if (error is ErrorEntity.Network) {
            _isNetworkReachable.emit(false)
            //TODO: registerBroadcast for internet changes
        } else {
            println("VIEWMODEL: UNEXPECTED ERROR")
        }
    }
}