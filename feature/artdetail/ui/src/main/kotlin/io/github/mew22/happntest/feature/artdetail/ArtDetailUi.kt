package io.github.mew22.happntest.feature.artdetail

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

@Immutable
data class ArtDetailUi(
    val id: String = "id1",
    val number: String = "number1",
    val title: String = "title1",
    val imageUrl: String = "https://lh3.googleusercontent.com/SsEIJWka3_cYRXXSE8VD3XNOgtOxoZhqW1" +
            "uB6UFj78eg8gq3G4jAqL4Z_5KwA12aD7Leqp27F653aBkYkRBkEQyeKxfaZPyDx0O8CzWg=s0",
    val description: String = "desc1",
    val colors: List<Color> = listOf(Color.Blue, Color.Red),
    val objectTypes: List<String> = listOf("type1", "type2"),
    val objectCollections: List<String> = listOf("collection1", "collection2"),
    val artists: List<ArtistUi> = listOf(
        ArtistUi("artist1", "nationality1"),
        ArtistUi("artist2", "nationality2"),
    )
)

@Immutable
data class ArtistUi(
    val name: String,
    val nationality: String,
)

fun ArtDetail.toUi() = ArtDetailUi(
    id = id,
    number = number,
    title = title,
    imageUrl = imageUrl.toString(),
    description = description,
    colors = colors.map { Color(it.value.toColorInt()) },
    objectTypes = objectTypes,
    objectCollections = objectCollections,
    artists = artists.map { it.toUi() }
)

fun Artist.toUi() = ArtistUi(
    name = name,
    nationality = nationality,
)
