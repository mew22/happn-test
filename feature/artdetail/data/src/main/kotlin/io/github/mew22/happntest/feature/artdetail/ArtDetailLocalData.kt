package io.github.mew22.happntest.feature.artdetail

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import io.github.mew22.happntest.feature.artdetail.ArtDetailDao.Companion.TABLE_NAME
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URL

@Entity(tableName = TABLE_NAME)
data class ArtDetailLocalData(
    @PrimaryKey val id: String,
    val number: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val colors: List<String>,
    val objectTypes: List<String>,
    val objectCollections: List<String>,
    val artists: List<ArtistLocalData>
)

@Serializable
data class ArtistLocalData(
    val name: String,
    val nationality: String,
) {
    object Converter {
        @TypeConverter
        fun toJson(data: ArtistLocalData): String = Json.encodeToString(data)

        @TypeConverter
        fun toObject(data: String): ArtistLocalData = Json.decodeFromString<ArtistLocalData>(data)
    }

    object ConverterList {
        @TypeConverter
        fun toJson(data: List<ArtistLocalData>): String = Json.encodeToString(data)

        @TypeConverter
        fun toObject(data: String): List<ArtistLocalData> = Json.decodeFromString<List<ArtistLocalData>>(data)
    }
}

fun ArtistLocalData.toDomain() = Artist(
    name = name,
    nationality = nationality
)

fun ArtistRemoteData.toLocal() = ArtistLocalData(
    name = name,
    nationality = nationality
)

fun ArtDetailLocalData.toDomain() = ArtDetail(
    id = id,
    number = number,
    title = title,
    description = description,
    imageUrl = URL(imageUrl),
    colors = colors.map { Color.getOrThrow(it.trim()) },
    objectTypes = objectTypes,
    objectCollections = objectCollections,
    artists = artists.map { it.toDomain() }
)

fun ArtDetailRemoteData.toLocal() = ArtDetailLocalData(
    id = id,
    number = objectNumber,
    title = title,
    description = description,
    imageUrl = webImage.url,
    colors = colors.map { it.hex },
    objectTypes = objectTypes,
    objectCollections = objectCollection,
    artists = principalMakers.map { it.toLocal() }
)
