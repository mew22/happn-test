package io.github.mew22.happntest

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import io.github.mew22.happntest.feature.artdetail.ArtDetailDatabase
import io.github.mew22.happntest.feature.artdetail.ArtDetailLocalData
import io.github.mew22.happntest.feature.artdetail.ArtistLocalData
import io.github.mew22.happntest.feature.artlist.ArtListDatabase
import io.github.mew22.happntest.feature.artlist.ArtListLocalData
import kotlinx.serialization.json.Json

@Database(
    version = 2,
    entities = [ArtListLocalData::class, ArtDetailLocalData::class],
    exportSchema = true
)
@TypeConverters(
    StringListConverter::class,
    ArtistLocalData.ConverterList::class,
    ArtistLocalData.Converter::class
)
abstract class Database : RoomDatabase(), ArtListDatabase, ArtDetailDatabase

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun toString(list: List<String>): String = Json.encodeToString(list)
}
