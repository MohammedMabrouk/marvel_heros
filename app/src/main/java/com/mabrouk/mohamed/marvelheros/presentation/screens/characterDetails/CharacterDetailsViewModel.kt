package com.mabrouk.mohamed.marvelheros.presentation.screens.characterDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterDataItem
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterDataType
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterInfoItem
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterItem
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharacterDataRequest
import com.mabrouk.mohamed.marvelheros.domain.usecase.GetCharacterDataUseCase
import com.mabrouk.mohamed.marvelheros.presentation.utils.network.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDataUseCase: GetCharacterDataUseCase,
) : ViewModel() {
    val pageSize = PAGE_SIZE

    private var comicsFullList: List<CharacterInfoItem?>? = null
    private var seriesFullList: List<CharacterInfoItem?>? = null
    private var storiesFullList: List<CharacterInfoItem?>? = null
    private var eventsFullList: List<CharacterInfoItem?>? = null

    private val _character =
        MutableStateFlow<CharacterItem?>(null)
    val character: StateFlow<CharacterItem?> = _character
    fun setCharacter(character: CharacterItem) {
        _character.value = character

        comicsFullList = character.comicsList
        seriesFullList = character.seriesList
        storiesFullList = character.storiesList
        eventsFullList = character.eventsList

        getComics(true)
        getSeries(true)
        getStories(true)
        getEvents(true)
    }

    // comics
    private val _comicsOffset = MutableStateFlow(0)
    private val comicsOffset: StateFlow<Int> = _comicsOffset

    private val _isLastPageComicsReached = MutableStateFlow(false)
    val isLastPageComicsReached: StateFlow<Boolean> = _isLastPageComicsReached

    private val _comicsList =
        MutableStateFlow<List<CharacterDataItem?>>(listOf())
    val comicsList: StateFlow<List<CharacterDataItem?>> = _comicsList

    fun getComics(isFirstLoad: Boolean) {
        if (isFirstLoad) {
            _comicsOffset.value = 0
            _comicsList.value = emptyList()
        }

        viewModelScope.launch {
            for (index in comicsOffset.value until comicsOffset.value + PAGE_SIZE) {
                if (!comicsFullList.isNullOrEmpty() && index < comicsFullList!!.size) {
                    val item = comicsFullList?.get(index)

                    getCharacterDataUseCase.getCharacterData(
                        GetCharacterDataRequest(
                            item?.id ?: 0,
                            CharacterDataType.COMIC
                        )
                    ).collect {
                        if (it is State.Success) {
                            _comicsList.value = comicsList.value.plus(it.data).distinct()
                        }
                    }
                    if (index == comicsOffset.value + PAGE_SIZE - 1) {
                        _isLastPageComicsReached.value =
                            comicsFullList!!.size <= comicsOffset.value + 1
                        _comicsOffset.value = comicsOffset.value + PAGE_SIZE
                    }
                }

            }
        }
    }

    // series
    private val _seriesOffset = MutableStateFlow(0)
    private val seriesOffset: StateFlow<Int> = _seriesOffset

    private val _isLastPageSeriesReached = MutableStateFlow(false)
    val isLastPageSeriesReached: StateFlow<Boolean> = _isLastPageSeriesReached

    private val _seriesList =
        MutableStateFlow<List<CharacterDataItem?>>(listOf())
    val seriesList: StateFlow<List<CharacterDataItem?>> = _seriesList

    fun getSeries(isFirstLoad: Boolean) {
        if (isFirstLoad) {
            _seriesOffset.value = 0
            _seriesList.value = emptyList()
        }

        viewModelScope.launch {
            for (index in seriesOffset.value until seriesOffset.value + PAGE_SIZE) {
                if (!seriesFullList.isNullOrEmpty() && index < seriesFullList!!.size) {
                    val item = seriesFullList?.get(index)

                    getCharacterDataUseCase.getCharacterData(
                        GetCharacterDataRequest(
                            item?.id ?: 0,
                            CharacterDataType.SERIES
                        )
                    ).collect {
                        if (it is State.Success) {
                            _seriesList.value = seriesList.value.plus(it.data).distinct()
                        }
                    }
                    if (index == seriesOffset.value + PAGE_SIZE - 1) {
                        _isLastPageSeriesReached.value =
                            seriesFullList!!.size <= seriesOffset.value + 1
                        _seriesOffset.value = seriesOffset.value + PAGE_SIZE
                    }
                }

            }
        }
    }

    // stories

    private val _storiesOffset = MutableStateFlow(0)
    private val storiesOffset: StateFlow<Int> = _storiesOffset

    private val _isLastPageStoriesReached = MutableStateFlow(false)
    val isLastPageStoriesReached: StateFlow<Boolean> = _isLastPageStoriesReached

    private val _storiesList =
        MutableStateFlow<List<CharacterDataItem?>>(listOf())
    val storiesList: StateFlow<List<CharacterDataItem?>> = _storiesList

    fun getStories(isFirstLoad: Boolean) {
        if (isFirstLoad) {
            _storiesOffset.value = 0
            _storiesList.value = emptyList()
        }

        viewModelScope.launch {
            for (index in storiesOffset.value until storiesOffset.value + PAGE_SIZE) {
                if (!storiesFullList.isNullOrEmpty() && index < storiesFullList!!.size) {
                    val item = storiesFullList?.get(index)

                    getCharacterDataUseCase.getCharacterData(
                        GetCharacterDataRequest(
                            item?.id ?: 0,
                            CharacterDataType.STORY
                        )
                    ).collect {
                        if (it is State.Success) {
                            _storiesList.value = storiesList.value.plus(it.data).distinct()
                        }
                    }
                    if (index == storiesOffset.value + PAGE_SIZE - 1) {
                        _isLastPageStoriesReached.value =
                            storiesFullList!!.size <= storiesOffset.value + 1
                        _storiesOffset.value = storiesOffset.value + PAGE_SIZE
                    }
                }

            }
        }
    }

    // events
    private val _eventsOffset = MutableStateFlow(0)
    private val eventsOffset: StateFlow<Int> = _eventsOffset

    private val _isLastPageEventsReached = MutableStateFlow(false)
    val isLastPageEventsReached: StateFlow<Boolean> = _isLastPageEventsReached

    private val _eventsList =
        MutableStateFlow<List<CharacterDataItem?>>(listOf())
    val eventsList: StateFlow<List<CharacterDataItem?>> = _eventsList

    fun getEvents(isFirstLoad: Boolean) {
        if (isFirstLoad) {
            _eventsOffset.value = 0
            _eventsList.value = emptyList()
        }

        viewModelScope.launch {
            for (index in eventsOffset.value until eventsOffset.value + PAGE_SIZE) {
                if (!eventsFullList.isNullOrEmpty() && index < eventsFullList!!.size) {
                    val item = eventsFullList?.get(index)

                    getCharacterDataUseCase.getCharacterData(
                        GetCharacterDataRequest(
                            item?.id ?: 0,
                            CharacterDataType.EVENT
                        )
                    ).collect {
                        if (it is State.Success) {
                            _eventsList.value = eventsList.value.plus(it.data).distinct()
                        }
                    }
                    if (index == eventsOffset.value + PAGE_SIZE - 1) {
                        _isLastPageEventsReached.value =
                            eventsFullList!!.size <= eventsOffset.value + 1
                        _eventsOffset.value = eventsOffset.value + PAGE_SIZE
                    }
                }

            }
        }
    }

    // expanded images dialog
    private val _showImagesDialog = MutableStateFlow(false)
    val showImagesDialog: StateFlow<Boolean> = _showImagesDialog
    fun setShowImagesDialog(show: Boolean){
        _showImagesDialog.value = show
    }

    private val _dialogType = MutableStateFlow(CharacterDataType.COMIC)
    val dialogType: StateFlow<CharacterDataType> = _dialogType
    fun setDialogType(type: CharacterDataType){
        _dialogType.value = type
    }

    private val _dialogStartIndex = MutableStateFlow(0)
    val dialogStartIndex: StateFlow<Int> = _dialogStartIndex
    fun setDialogStartIndex(index: Int){
        _dialogStartIndex.value = index
    }

    companion object {
        private const val PAGE_SIZE = 4
    }
}