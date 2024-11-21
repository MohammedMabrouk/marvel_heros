package com.mabrouk.mohamed.marvelheros.data.remote

import com.mabrouk.mohamed.marvelheros.data.response.GetCharacterDataResponseDto
import com.mabrouk.mohamed.marvelheros.data.response.GetCharactersResponseDto
import com.mabrouk.mohamed.marvelheros.presentation.utils.Params.COMIC_ID
import com.mabrouk.mohamed.marvelheros.presentation.utils.Params.EVENT_ID
import com.mabrouk.mohamed.marvelheros.presentation.utils.Params.SERIES_ID
import com.mabrouk.mohamed.marvelheros.presentation.utils.Params.STORY_ID
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("comics/{$COMIC_ID}")
    suspend fun getComic(
        @Path(COMIC_ID) comicId: Int,
        @Query("apikey") apikey: String,
        @Query("ts") timeStamp: String,
        @Query("hash") hash: String,
    ): GetCharacterDataResponseDto

    @GET("series/{$SERIES_ID}")
    suspend fun getSeries(
        @Path(SERIES_ID) comicId: Int,
        @Query("apikey") apikey: String,
        @Query("ts") timeStamp: String,
        @Query("hash") hash: String,
    ): GetCharacterDataResponseDto

    @GET("stories/{$STORY_ID}")
    suspend fun getStory(
        @Path(STORY_ID) comicId: Int,
        @Query("apikey") apikey: String,
        @Query("ts") timeStamp: String,
        @Query("hash") hash: String,
    ): GetCharacterDataResponseDto

    @GET("events/{$EVENT_ID}")
    suspend fun getEvent(
        @Path(EVENT_ID) comicId: Int,
        @Query("apikey") apikey: String,
        @Query("ts") timeStamp: String,
        @Query("hash") hash: String,
    ): GetCharacterDataResponseDto
}