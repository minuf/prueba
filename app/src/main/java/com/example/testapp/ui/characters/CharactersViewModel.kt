package com.example.testapp.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.core.domain.model.Character
import com.example.testapp.core.domain.ports.GetAllCharacters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersViewModel : ViewModel() {
    @Inject lateinit var getAllCharacters: GetAllCharacters

    private val _characters = MutableLiveData<List<Character>>()

    val characters: LiveData<List<Character>> = _characters

    fun fetchCharacters() {
        viewModelScope.launch {
            val characters = withContext(Dispatchers.IO) {
                getAllCharacters.run()
            }
            _characters.value = characters
        }
    }
}