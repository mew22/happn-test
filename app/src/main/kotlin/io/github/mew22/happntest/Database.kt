package io.github.mew22.happntest

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.mew22.happntest.feature.artlist.ArtListDatabase
import io.github.mew22.happntest.feature.artlist.ArtListLocalData

@Database(
    version = 2,
    entities = [ArtListLocalData::class],
    exportSchema = true
)
abstract class Database : RoomDatabase(), ArtListDatabase
