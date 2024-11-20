package com.mabrouk.mohamed.marvelheros.domain.usecase

import com.mabrouk.mohamed.marvelheros.domain.data.CharacterItem
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersRequest
import com.mabrouk.mohamed.marvelheros.presentation.utils.network.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetCharactersListUseCase @Inject constructor(

) {

    fun getMockCharactersList(getCharactersRequest: GetCharactersRequest): Flow<State<List<CharacterItem>>> =
        flow {
            Timber.d("getMockCharacterList")
            emit(State.Loading)
            delay(2000)
//            emit(getMockData(getCharactersRequest.offset))
            emit(State.Error("error msg"))
        }

    private fun getMockData(page: Int): State.Success<List<CharacterItem>> {
        val data = listOf(
            CharacterItem(
                0,
                "A-Bomb (HAS) - $page",
                "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg"
            ),
            CharacterItem(
                0,
                "A.I.M. - $page",
                "http://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec.jpg"
            ),
            CharacterItem(
                0,
                "Aaron Stack - $page",
                "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg"
            ),
            CharacterItem(
                0,
                "Absorbing Man - $page",
                "http://i.annihil.us/u/prod/marvel/i/mg/1/b0/5269678709fb7.jpg"
            )
        )
        return State.Success(data)
    }
}