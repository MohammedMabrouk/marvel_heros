package com.mabrouk.mohamed.marvelheros.data.response


import com.google.gson.annotations.SerializedName

data class GetCharacterDataResponseDto(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: Data?,
) {
    data class Data(
        @SerializedName("results")
        val results: List<Result?>?,
    ) {
        data class Result(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("title")
            val title: String?,
            @SerializedName("thumbnail")
            val thumbnail: Thumbnail?,
        ) {
            data class Thumbnail(
                @SerializedName("path")
                val path: String?,
                @SerializedName("extension")
                val extension: String?,
            )
        }
    }
}