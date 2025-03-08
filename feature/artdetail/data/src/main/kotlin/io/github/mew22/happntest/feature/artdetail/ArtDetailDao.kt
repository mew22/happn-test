package io.github.mew22.happntest.feature.artdetail

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(art: ArtDetailLocalData)

    @Query("select * from $TABLE_NAME where number = :objectNumber")
    fun get(objectNumber: String): Flow<ArtDetailLocalData?>

    companion object {
        const val TABLE_NAME = "artDetail"
    }
}
