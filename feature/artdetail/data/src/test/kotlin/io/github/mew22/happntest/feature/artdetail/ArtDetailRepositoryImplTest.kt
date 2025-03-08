package io.github.mew22.happntest.feature.artdetail

import io.github.mew22.happntest.core.network.NetworkException
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ArtDetailRepositoryImplTest {

    private val service: ArtDetailService = mockk()
    private val dao: ArtDetailDao = mockk()

    private val objectNumber = "1"
    private val mockJsonArtData: ArtDetailRemoteData = mockk()
    private val mockLocalArtData: ArtDetailLocalData = mockk()
    private val mockArtDetail: ArtDetail = mockk()
    private val mockJsonResponse: ArtDetailDataResponse = ArtDetailDataResponse(
        artObject = mockJsonArtData
    )

    private val repository = ArtDetailRepositoryImpl(
        artDetailService = service,
        artDetailDao = dao
    )

    @Nested
    inner class FetchDetailTesting {

        @Test
        fun `WHEN fetchDetail is called THEN load from service success`() = runTest {
            coEvery {
                service.fetchArtDetail(objectNumber)
            } returns Result.success(mockJsonResponse)

            mockkStatic(ArtDetailRemoteData::toLocal) {
                every { mockJsonArtData.toLocal() } returns mockLocalArtData
                coJustRun { dao.insert(mockLocalArtData) }
                mockkStatic(ArtDetailRemoteData::toDomain) {
                    every { mockJsonArtData.toDomain() } returns mockArtDetail
                    val result = repository.fetchDetail(objectNumber)
                    assertEquals(result.getOrThrow(), mockArtDetail)
                }
            }
            coVerify(exactly = 1) { dao.insert(mockLocalArtData) }
        }

        @Test
        fun `WHEN fetchDetail is called THEN load from service failure`() = runTest {
            val failure = NetworkException(httpCode = 404)
            coEvery {
                service.fetchArtDetail(objectNumber)
            } returns Result.failure(failure)

            val result = repository.fetchDetail(objectNumber)

            assertEquals(result.exceptionOrNull(), failure)
        }
    }

    @Nested
    inner class GetDetailTesting {

        @Test
        fun `WHEN getDetail is called THEN load from dao success`() = runTest {
            every {
                dao.get(objectNumber)
            } returns flowOf(mockLocalArtData)

            mockkStatic(ArtDetailLocalData::toDomain) {
                every { mockLocalArtData.toDomain() } returns mockArtDetail
                val result = repository.getDetail(objectNumber).first()
                assertEquals(result, mockArtDetail)
            }
        }
    }
}
