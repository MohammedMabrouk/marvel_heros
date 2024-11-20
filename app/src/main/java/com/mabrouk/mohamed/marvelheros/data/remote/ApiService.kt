package com.mabrouk.mohamed.marvelheros.data.remote

import com.mabrouk.mohamed.marvelheros.data.response.GetCharactersResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("characters")
    suspend fun getCharacters(
        @Query("apikey") apikey: String,
        @Query("ts") timeStamp: String,
        @Query("hash") hash: String,
        @Query("limit") limit: String,
        @Query("offset") offset: String?,
        @Query("nameStartsWith") searchQuery: String?,
    ): GetCharactersResponseDto
}