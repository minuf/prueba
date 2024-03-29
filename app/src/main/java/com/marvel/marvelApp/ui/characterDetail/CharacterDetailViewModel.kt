package com.marvel.marvelApp.ui.characterDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvel.domain.model.Character
import com.marvel.domain.model.Result
import com.marvel.domain.usecases.GetCharacterByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel() {

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