package com.mabrouk.mohamed.marvelheros.domain.data

data class GetCharactersDto(
    val totalElements: Int,
    val data: List<CharacterItem?>?,
)