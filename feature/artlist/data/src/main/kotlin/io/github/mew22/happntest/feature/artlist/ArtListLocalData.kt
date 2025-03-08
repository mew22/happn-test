package io.github.mew22.happntest.feature.artlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.mew22.happntest.feature.artlist.ArtListDao.Companion.TABLE_NAME
import java.net.URL

@Entity(tableName = TABLE_NAME)
data class ArtListLocalData(
    @PrimaryKey val id: String,
    val number: String,
    val title: String,
    val artistName: String,
    val imageUrl: String
)

fun ArtListLocalData.toDomain() = ArtList(
    id = id,
    number = number,
    title = title,
    artistName = artistName,
    imageUrl = URL(imageUrl),
)

fun ArtListRemoteData.toLocal() = ArtListLocalData(
    id = id,
    number = objectNumber,
    title = title,
    artistName = principalOrFirstMaker,
    imageUrl = webImage.url,
)
