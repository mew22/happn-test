package io.github.mew22.happntest.feature.artlist

import androidx.compose.runtime.Immutable

@Immutable
data class ArtListUi(
    val id: String,
    val number: String,
    val title: String,
    val artistName: String,
    val imageUrl: String
)

fun ArtList.toUi() = ArtListUi(
    id = id,
    number = number,
    title = title,
    artistName = artistName,
    imageUrl = imageUrl.toString()
)
