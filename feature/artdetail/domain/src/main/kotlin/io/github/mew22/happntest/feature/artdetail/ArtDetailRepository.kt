package io.github.mew22.happntest.feature.artdetail

import kotlinx.coroutines.flow.Flow

interface ArtDetailRepository {
    suspend fun fetchDetail(id: String): Result<ArtDetail>
    fun getDetail(id: String): Flow<ArtDetail?>
}
