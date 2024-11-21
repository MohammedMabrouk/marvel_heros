package com.mabrouk.mohamed.marvelheros.domain.usecase

import com.mabrouk.mohamed.marvelheros.domain.data.CharacterDataItem
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharacterDataRequest
import com.mabrouk.mohamed.marvelheros.domain.repository.MarvelRepository
import com.mabrouk.mohamed.marvelheros.presentation.utils.network.State
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterDataUseCase @Inject constructor(
    private val repository: MarvelRepository,
) {
    fun getCharacterData(request: GetCharacterDataRequest): Flow<State<CharacterDataItem>> =
        flow {
            emit(State.Loading)
            try {
                val response = repository.getCharacterInfo(request)
                emit(State.Success(response))
            } catch (e: Exception) {
                emit(State.Error(e.message ?: ""))
            }
        }
}