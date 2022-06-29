package com.example.testapp.ui.characters
import com.example.testapp.core.domain.model.Character
import com.example.testapp.core.domain.ports.GetAllCharacters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersViewModel(private val viewModelScope: CoroutineScope) {
    @Inject
    lateinit var getAllCharacters: GetAllCharacters

    private val _characters = MutableStateFlow<List<Character>>(listOf())

    val characters: StateFlow<List<Character>> = _characters

    fun fetchCharacters() {
        viewModelScope.launch {
            _characters.value = withContext(Dispatchers.IO) {
                getAllCharacters.run()
            }
        }
    }
}