package com.mabrouk.mohamed.marvelheros.domain.usecase

import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersDto
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersRequest
import com.mabrouk.mohamed.marvelheros.domain.repository.MarvelRepository
import com.mabrouk.mohamed.marvelheros.presentation.utils.network.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharactersListUseCase @Inject constructor(
    private val repository: MarvelRepository,
) {
    fun getCharactersList(getCharactersRequest: GetCharactersRequest): Flow<State<GetCharactersDto>> =
        flow {
            emit(State.Loading)
            try {
                val response = repository.getCharactersList(getCharactersRequest)
                emit(State.Success(response))
            } catch (e: Exception) {
                emit(State.Error(e.message ?: ""))
            }
        }
}