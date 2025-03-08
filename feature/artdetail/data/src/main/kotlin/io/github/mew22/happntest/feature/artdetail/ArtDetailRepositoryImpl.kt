package io.github.mew22.happntest.feature.artdetail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArtDetailRepositoryImpl(
    private val artDetailService: ArtDetailService,
    private val artDetailDao: ArtDetailDao
) : ArtDetailRepository {
    override suspend fun fetchDetail(id: String): Result<ArtDetail> =
        artDetailService.fetchArtDetail(id).onSuccess {
            artDetailDao.insert(it.artObject.toLocal())
        }.mapCatching {
            it.artObject.toDomain()
        }

    override fun getDetail(id: String): Flow<ArtDetail?> = artDetailDao.get(id).map { it?.toDomain() }
}
