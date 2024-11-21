package com.mabrouk.mohamed.marvelheros.domain.data

data class GetCharacterDataRequest(
    val id: Int,
    val characterDataType: CharacterDataType,
)

enum class CharacterDataType {
    COMIC,
    SERIES,
    STORY,
    EVENT
}