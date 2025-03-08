package io.github.mew22.happntest.feature.artdetail

import com.squareup.moshi.JsonClass
import java.net.URL

@JsonClass(generateAdapter = true)
data class ArtDetailDataResponse(
    val artObject: ArtDetailRemoteData
)

@JsonClass(generateAdapter = true)
data class ArtDetailRemoteData(
    val id: String,
    val objectNumber: String,
    val title: String,
    val description: String,
    val webImage: WebImage,
    val colors: List<ColorRemoteData>,
    val objectTypes: List<String>,
    val objectCollection: List<String>,
    val principalMakers: List<ArtistRemoteData>
)

@JsonClass(generateAdapter = true)
data class WebImage(
    val url: String
)

@JsonClass(generateAdapter = true)
data class ColorRemoteData(
    val hex: String,
    val percentage: Int
)

@JsonClass(generateAdapter = true)
data class ArtistRemoteData(
    val name: String,
    val nationality: String,
)

fun ArtDetailRemoteData.toDomain() = ArtDetail(
    id = id,
    number = objectNumber,
    title = title,
    imageUrl = URL(webImage.url),
    description = description,
    colors = colors.map { Color.getOrThrow(it.hex.trim()) },
    objectTypes = objectTypes,
    objectCollections = objectCollection,
    artists = principalMakers.map { it.toDomain() }
)

fun ArtistRemoteData.toDomain() = Artist(
    name = name,
    nationality = nationality
)
