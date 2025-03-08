package io.github.mew22.happntest.feature.artdetail

import io.github.mew22.happntest.core.common.ValueObjectCreator
import java.net.URL

data class ArtDetail(
    val id: String,
    val number: String,
    val imageUrl: URL,
    val title: String,
    val description: String,
    val colors: List<Color>,
    val objectTypes: List<String>,
    val objectCollections: List<String>,
    val artists: List<Artist>
)

data class Artist(
    val name: String,
    val nationality: String,
)

@JvmInline
value class Color private constructor(val value: String) {
    companion object : ValueObjectCreator<String, Color, IllegalArgumentException>() {
        override val construction: (String) -> Color = ::Color
        override fun isValid(input: String): Boolean =
            input.matches(Regex(pattern = "^#([0-9a-fA-F]{2}){3}$"))
    }
}
