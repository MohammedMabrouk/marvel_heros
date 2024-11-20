package com.mabrouk.mohamed.marvelheros.presentation.screens.characterDetails

import androidx.lifecycle.ViewModel
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(

) : ViewModel() {

    private val _character =
        MutableStateFlow<CharacterItem?>(null)
    val character: StateFlow<CharacterItem?> = _character
    fun setCharacter(character: CharacterItem) {
        _character.value = character
    }
}