package com.mabrouk.mohamed.marvelheros.domain.repository

import com.mabrouk.mohamed.marvelheros.domain.data.CharacterDataItem
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharacterDataRequest
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersDto
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersRequest

interface MarvelRepository {
    suspend fun getCharactersList(getCharactersRequest: GetCharactersRequest): GetCharactersDto
    suspend fun getCharacterInfo(getCharacterDataRequest: GetCharacterDataRequest): CharacterDataItem
}