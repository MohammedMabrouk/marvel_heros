package com.mabrouk.mohamed.marvelheros.data.mapper

import com.mabrouk.mohamed.marvelheros.data.getLastIntAfterSlash
import com.mabrouk.mohamed.marvelheros.data.response.GetCharacterDataResponseDto
import com.mabrouk.mohamed.marvelheros.data.response.GetCharactersResponseDto
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterDataItem
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterInfoItem
import com.mabrouk.mohamed.marvelheros.domain.data.CharacterItem
import com.mabrouk.mohamed.marvelheros.domain.data.GetCharactersDto

fun GetCharactersResponseDto.mapToDomain(): GetCharactersDto {
    return GetCharactersDto(
        totalElements = this.data?.total ?: 0,
        data = this.data?.results?.map { item ->
            val thumbnailPath = item?.thumbnail?.path ?: ""
            val thumbnailExtension = item?.thumbnail?.extension ?: ""
            val fullThumbnailUrl =
                if (thumbnailPath.isNotEmpty()) "${thumbnailPath}.${thumbnailExtension}" else ""
            CharacterItem(
                id = item?.id,
                name = item?.name ?: "",
                thumbnailUrl = fullThumbnailUrl,
                description = item?.description,
                comicsList = item?.comics?.items?.map { it?.mapToDomain() },
                seriesList = item?.series?.items?.map { it?.mapToDomain() },
                storiesList = item?.stories?.items?.map { it?.mapToDomain() },
                eventsList = item?.events?.items?.map { it?.mapToDomain() },
                detailLink = item?.urls?.find { it?.type == "detail" }?.url,
                wikiLink = item?.urls?.find { it?.type == "wiki" }?.url,
                comicLink = item?.urls?.find { it?.type == "comiclink" }?.url
            )
        }
    )
}

fun GetCharactersResponseDto.Data.Result.Comics.Item.mapToDomain(): CharacterInfoItem {
    return CharacterInfoItem(
        id = getLastIntAfterSlash(this.resourceURI ?: ""),
        name = this.name,
        resourceURI = this.resourceURI
    )
}

fun GetCharactersResponseDto.Data.Result.Series.Item.mapToDomain(): CharacterInfoItem {
    return CharacterInfoItem(
        id = getLastIntAfterSlash(this.resourceURI ?: ""),
        name = this.name,
        resourceURI = this.resourceURI
    )
}

fun GetCharactersResponseDto.Data.Result.Stories.Item.mapToDomain(): CharacterInfoItem {
    return CharacterInfoItem(
        id = getLastIntAfterSlash(this.resourceURI ?: ""),
        name = this.name,
        resourceURI = this.resourceURI
    )
}

fun GetCharactersResponseDto.Data.Result.Events.Item.mapToDomain(): CharacterInfoItem {
    return CharacterInfoItem(
        id = getLastIntAfterSlash(this.resourceURI ?: ""),
        name = this.name,
        resourceURI = this.resourceURI
    )
}

fun GetCharacterDataResponseDto.mapToDomain(): CharacterDataItem {
    val thumbnailPath = this.data?.results?.firstOrNull()?.thumbnail?.path ?: ""
    val thumbnailExtension = this.data?.results?.firstOrNull()?.thumbnail?.extension ?: ""
    val fullThumbnailUrl =
        if (thumbnailPath.isNotEmpty()) "${thumbnailPath}.${thumbnailExtension}" else ""

    return CharacterDataItem(
        id = this.data?.results?.firstOrNull()?.id,
        name = this.data?.results?.firstOrNull()?.title ?: "",
        thumbnailUrl = fullThumbnailUrl,
    )
}