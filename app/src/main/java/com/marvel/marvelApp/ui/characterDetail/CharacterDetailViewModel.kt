package com.marvel.marvelApp.ui.characterDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.marvel.model.Character
import com.marvel.model.Result
import com.marvel.usecases.GetCharacterByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailViewModel(private val getCharacterByIdUseCase: GetCharacterByIdUseCase) : ViewModel() {

    private val _character = MutableStateFlow<Character?>(null)

    val character: StateFlow<Character?> = _character

    fun fetchCharacter(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getCharacterByIdUseCase(id).let { result ->
                if (result is Result.Success) {
                    _character.value = result.data
                } else if (result is Result.Error) {
                    println("CharacterDetailViewModel Error: " + result.error)
                }
            }
        }
    }
}

class CharacterDetailViewModelFactory(private val getCharacterByIdUseCase: GetCharacterByIdUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterDetailViewModel(getCharacterByIdUseCase) as T
    }
}