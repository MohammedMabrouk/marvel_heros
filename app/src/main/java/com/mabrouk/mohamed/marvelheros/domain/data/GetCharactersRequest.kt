package com.mabrouk.mohamed.marvelheros.domain.data

data class GetCharactersRequest(
    val limit: Int,
    val offset: Int,
    val searchQuery: String?,
)