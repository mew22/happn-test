package io.github.mew22.happntest.core.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

class DatabaseFactory(
    private val context: Context
) : DatabaseHelper {

    override fun <T> create(clazz: Class<T>, name: String): T =
        Room.databaseBuilder(
            context.applicationContext,
            clazz.asSubclass(RoomDatabase::class.java),
            name
        )
            .fallbackToDestructiveMigration(false)
            .build() as T
}
