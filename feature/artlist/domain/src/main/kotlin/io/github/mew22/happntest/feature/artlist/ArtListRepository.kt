package io.github.mew22.happntest.feature.artlist

import kotlinx.coroutines.flow.Flow

interface ArtListRepository {
    val artList: Flow<List<ArtList>>
    suspend fun fetchArtList(): Result<List<ArtList>>
}
