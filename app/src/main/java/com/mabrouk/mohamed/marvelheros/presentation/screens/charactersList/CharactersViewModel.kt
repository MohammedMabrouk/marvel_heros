package com.mabrouk.mohamed.marvelheros.presentation.screens.charactersList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterItem
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersRequest
import com.mabrouk.mohamed.marvelheros.domain.usecase.GetCharactersListUseCase
import com.mabrouk.mohamed.marvelheros.presentation.utils.asStateFlow
import com.mabrouk.mohamed.marvelheros.presentation.utils.network.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersListUseCase: GetCharactersListUseCase,
) : ViewModel() {

    private val _charactersResult =
        MutableStateFlow<State<List<CharacterItem>>>(State.Empty)
    val charactersResult: StateFlow<State<List<CharacterItem>>> = _charactersResult
    fun resetResult(){
        _charactersResult.value = State.Empty
    }

    private val _charactersList =
        MutableStateFlow<List<CharacterItem>?>(null)
    val charactersList: StateFlow<List<CharacterItem>?> = _charactersList

    val isLoading: StateFlow<Boolean> = charactersResult.map {
        it == State.Loading
    }.asStateFlow(viewModelScope, false)

    private val _isLastPageReached = MutableStateFlow(false)
    val isLastPageReached: StateFlow<Boolean> = _isLastPageReached

    private val _offset = MutableStateFlow(0)
    private val offset: StateFlow<Int> = _offset

    private var charactersJob: Job? = null

    fun getNextCharacters(isFirstLoad: Boolean, searchQuery: String? = null) {
        if (charactersResult.value == State.Loading) return

        if (isFirstLoad) {
            _offset.value = 0
            _charactersList.value = emptyList()
        }

        // cancel if already running
        charactersJob?.cancel()
        charactersJob = viewModelScope.launch {
            try {
                // delay if search is used
                if (!searchQuery.isNullOrEmpty())
                    delay(2000)

                val request = GetCharactersRequest(
                    limit = PAGE_SIZE,
                    offset = offset.value,
                    searchQuery = searchQuery
                )

                Log.d("www", "--> ${request}")
                getCharactersListUseCase.getMockCharactersList(request).collect { result ->
                    _charactersResult.value = result
                    if (result is State.Success) {
                        result.data.toList().let {
                            _charactersList.value = _charactersList.value?.plus(it)
                        }
                        // todo: check if last page 
//                        _isLastPageReached.value = result.data.meta?.lastPage ?: false
                        _offset.value = offset.value + 1
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object{
        private const val PAGE_SIZE = 10
    }
}