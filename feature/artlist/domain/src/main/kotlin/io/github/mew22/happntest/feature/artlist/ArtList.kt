package io.github.mew22.happntest.feature.artlist

import java.net.URL

data class ArtList(
    val id: String,
    val number: String,
    val title: String,
    val artistName: String,
    val imageUrl: URL
)
