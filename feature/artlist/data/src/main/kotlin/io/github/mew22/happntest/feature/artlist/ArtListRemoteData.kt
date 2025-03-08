package io.github.mew22.happntest.feature.artlist

import com.squareup.moshi.JsonClass
import java.net.URL

@JsonClass(generateAdapter = true)
data class ArtListDataResponse(
    val artObjects: List<ArtListRemoteData>
)

@JsonClass(generateAdapter = true)
data class ArtListRemoteData(
    val id: String,
    val objectNumber: String,
    val title: String,
    val principalOrFirstMaker: String,
    val webImage: WebImage
)

@JsonClass(generateAdapter = true)
data class WebImage(
    val url: String
)

fun ArtListRemoteData.toDomain() = ArtList(
    id = id,
    number = objectNumber,
    title = title,
    artistName = principalOrFirstMaker,
    imageUrl = URL(webImage.url),
)
