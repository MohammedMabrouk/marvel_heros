package com.mabrouk.mohamed.marvelheros.domain.repository

import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersDto
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersRequest

interface MarvelRepository {
    suspend fun getCharactersList(getCharactersRequest: GetCharactersRequest): GetCharactersDto
}