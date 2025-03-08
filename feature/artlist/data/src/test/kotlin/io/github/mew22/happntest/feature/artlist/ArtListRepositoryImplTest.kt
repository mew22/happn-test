package io.github.mew22.happntest.feature.artlist

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

class ArtListRepositoryImplTest {

    private val service: ArtListService = mockk()
    private val dao: ArtListDao = mockk()

    private val mockJsonArtData: ArtListRemoteData = mockk()
    private val mockLocalArtData: ArtListLocalData = mockk()
    private val mockArtList: ArtList = mockk()
    private val mockJsonResponse: ArtListDataResponse = ArtListDataResponse(
        artObjects = listOf(mockJsonArtData)
    )

    private val repository = ArtListRepositoryImpl(
        artListService = service,
        artListDao = dao
    )

    @Nested
    inner class FetchListTesting {

        @Test
        fun `WHEN fetchList is called THEN load from service success`() = runTest {
            coEvery {
                service.fetchArtList()
            } returns Result.success(mockJsonResponse)

            mockkStatic(ArtListRemoteData::toLocal) {
                every { mockJsonArtData.toLocal() } returns mockLocalArtData
                coJustRun { dao.insertAll(listOf(mockLocalArtData)) }
                mockkStatic(ArtListRemoteData::toDomain) {
                    every { mockJsonArtData.toDomain() } returns mockArtList
                    val result = repository.fetchArtList()
                    assertEquals(result.getOrThrow(), listOf(mockArtList))
                }
            }
            coVerify(exactly = 1) { dao.insertAll(listOf(mockLocalArtData)) }
        }

        @Test
        fun `WHEN fetchList is called THEN load from service failure`() = runTest {
            val failure = NetworkException(httpCode = 404)
            coEvery {
                service.fetchArtList()
            } returns Result.failure(failure)

            val result = repository.fetchArtList()

            assertEquals(result.exceptionOrNull(), failure)
        }
    }

    @Nested
    inner class GetArtListTesting {

        @Test
        fun `WHEN artList is called THEN load from dao success`() = runTest {
            every {
                dao.getAll()
            } returns flowOf(listOf(mockLocalArtData))

            mockkStatic(ArtListLocalData::toDomain) {
                every { mockLocalArtData.toDomain() } returns mockArtList
                val result = repository.artList.first()
                assertEquals(result, listOf(mockArtList))
            }
        }
    }
}
