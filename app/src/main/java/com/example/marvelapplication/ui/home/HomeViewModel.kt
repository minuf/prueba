package com.example.marvelapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapplication.core.domain.model.Character
import com.example.marvelapplication.core.domain.ports.AppPortsFactory
import com.example.marvelapplication.core.domain.ports.GetAllCharacters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    //TODO: inject this action/port
    private val getAllCharacters: GetAllCharacters = AppPortsFactory().getAllCharacters

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

    init {
        fetchCharacters()
    }
}