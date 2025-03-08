package io.github.mew22.happntest.feature.artlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArtListRepositoryImpl(
    private val artListService: ArtListService,
    private val artListDao: ArtListDao
) : ArtListRepository {
    override val artList: Flow<List<ArtList>> by lazy {
        artListDao.getAll().map { localList -> localList.map { it.toDomain() } }
    }

    override suspend fun fetchArtList(): Result<List<ArtList>> = artListService.fetchArtList()
        .onSuccess { response ->
            artListDao.insertAll(response.artObjects.map { it.toLocal() })
        }
        .mapCatching { response ->
            response.artObjects.map { it.toDomain() }
        }
}
