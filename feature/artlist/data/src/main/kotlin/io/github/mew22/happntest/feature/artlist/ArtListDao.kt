package io.github.mew22.happntest.feature.artlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ArtListLocalData>)

    @Query("select * from $TABLE_NAME")
    fun getAll(): Flow<List<ArtListLocalData>>

    companion object {
        const val TABLE_NAME = "artList"
    }
}
