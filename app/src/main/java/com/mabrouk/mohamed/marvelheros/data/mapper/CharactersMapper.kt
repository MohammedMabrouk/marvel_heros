package com.mabrouk.mohamed.marvelheros.data.mapper

import com.mabrouk.mohamed.marvelheros.data.response.GetCharactersResponseDto
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterItem
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersDto

fun GetCharactersResponseDto.mapToDomain(): GetCharactersDto {
    return GetCharactersDto(
        totalElements = this.data?.total ?: 0,
        data = this.data?.results?.map { item ->
            val thumbnailPath = item?.thumbnail?.path ?: ""
            val thumbnailExtension = item?.thumbnail?.extension ?: ""
            CharacterItem(
                id = item?.id,
                name = item?.name ?: "",
                thumbnailUrl = "${thumbnailPath}.${thumbnailExtension}"
            )
        }
    )
}