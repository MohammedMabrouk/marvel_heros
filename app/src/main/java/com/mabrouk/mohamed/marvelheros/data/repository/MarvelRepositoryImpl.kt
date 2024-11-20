package com.mabrouk.mohamed.marvelheros.data.repository

import com.mabrouk.mohamed.marvelheros.BuildConfig
import com.mabrouk.mohamed.marvelheros.data.generateMarvelApiHash
import com.mabrouk.mohamed.marvelheros.data.mapper.mapToDomain
import com.mabrouk.mohamed.marvelheros.data.remote.ApiService
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersDto
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersRequest
import com.mabrouk.mohamed.marvelheros.domain.repository.MarvelRepository
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    MarvelRepository {

    private val apiKey = BuildConfig.MARVEL_PUBLIC_KEY
    private val privateKey = BuildConfig.MARVEL_PRIVATE_KEY

    override suspend fun getCharactersList(getCharactersRequest: GetCharactersRequest): GetCharactersDto {
        val timestamp = System.currentTimeMillis().toString()
        val hash = generateMarvelApiHash(timestamp, apiKey, privateKey)

        return apiService.getCharacters(
            apikey = apiKey,
            timeStamp = timestamp,
            hash = hash,
            limit = getCharactersRequest.limit.toString(),
            offset = getCharactersRequest.offset.toString(),
            searchQuery = getCharactersRequest.searchQuery
        ).mapToDomain()
    }
}