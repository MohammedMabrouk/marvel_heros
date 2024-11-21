package com.mabrouk.mohamed.marvelheros.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterItem(
    val id: Int?,
    val name: String,
    val thumbnailUrl: String,
    val description: String? = null,
    val comicsList: List<CharacterInfoItem?>? = null,
    val seriesList: List<CharacterInfoItem?>? = null,
    val storiesList: List<CharacterInfoItem?>? = null,
    val eventsList: List<CharacterInfoItem?>? = null,
    val detailLink: String? = null,
    val wikiLink: String? = null,
    val comicLink: String? = null,
) : Parcelable

@Parcelize
data class CharacterInfoItem(
    val id: Int?,
    val name: String?,
    val resourceURI: String?
) : Parcelable