package com.mabrouk.mohamed.marvelheros.presentation.screens.charactersList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterItem
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersDto
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
        MutableStateFlow<State<GetCharactersDto>>(State.Empty)
    val charactersResult: StateFlow<State<GetCharactersDto>> = _charactersResult
    fun resetResult() {
        _charactersResult.value = State.Empty
    }

    private val _charactersList =
        MutableStateFlow<List<CharacterItem?>?>(null)
    val charactersList: StateFlow<List<CharacterItem?>?> = _charactersList

    val isLoading: StateFlow<Boolean> = charactersResult.map {
        it == State.Loading
    }.asStateFlow(viewModelScope, false)

    private val _isLastPageReached = MutableStateFlow(false)
    val isLastPageReached: StateFlow<Boolean> = _isLastPageReached

    private val _offset = MutableStateFlow(0)
    private val offset: StateFlow<Int> = _offset

    private var charactersJob: Job? = null

    fun getPagedCharacters(isFirstLoad: Boolean, searchQuery: String? = null) {
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
                getCharactersListUseCase.getCharactersList(request).collect { result ->
                    _charactersResult.value = result
                    if (result is State.Success) {
                        result.data.data?.toList()?.let {
                            _charactersList.value = charactersList.value?.plus(it)
                        }

                        _isLastPageReached.value = result.data.totalElements < offset.value + 1
                        _offset.value = offset.value + PAGE_SIZE
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}