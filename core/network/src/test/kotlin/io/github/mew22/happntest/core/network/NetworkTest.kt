package io.github.mew22.happntest.core.network

import io.github.mew22.happntest.core.environment.EnvironmentGateway
import io.mockk.every
import io.mockk.isMockKMock
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

internal class NetworkTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(networkModule)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    val server = MockWebServer()

    lateinit var environmentGateway: EnvironmentGateway

    private fun createTestService(): TestService =
        koinTestRule.koin.get<NetworkBuilder>().build().create<TestService> { mockk() }

    @Before
    fun setUp() {
        server.start()

        environmentGateway = koinTestRule.koin.declareMock<EnvironmentGateway> {
            every { isDebug } returns false
            every { isMock } returns false
            every { androidVersion } returns 27
            every { apiKey } returns ""
        }

        koinTestRule.koin.declareMock<Api> {
            every { getApi() } returns server.url("/v1/test/").toString()
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `Service method is not suspend`() {
        // When
        val service = createTestService()

        // Then
        assertFailsWith<IllegalArgumentException> {
            service.getTodosSync()
        }
    }

    @Test
    fun `Service method does not return Result type`() = runTest {
        // When
        val service = createTestService()

        // then
        assertFailsWith<IllegalArgumentException> {
            service.getTodosWithoutResult()
        }
    }

    @Test
    fun `Create real service`() {
        // When
        val service = createTestService()

        // Then
        assertIs<TestService>(service)
        assertFalse(isMockKMock(service))
    }

    @Test
    fun `Create mock service`() {
        // Given
        every { environmentGateway.isMock } returns true

        // When
        val service = createTestService()

        // Then
        assertIs<TestService>(service)
        assertTrue(isMockKMock(service))
    }

    @Test
    fun `Return successful response from server`() = runTest {
        // Given
        val mockResponse = MockResponse().setBody(sampleResponseBody)
        server.enqueue(mockResponse)

        // When
        val service = createTestService()
        val result = service.getTodos()

        // Then
        assertTrue(result.isSuccess)
        assertContentEquals(sampleResponse, result.getOrNull())
    }

    @Test
    fun `Return error response from server`() = runTest {
        // Given
        val mockResponse = MockResponse().setResponseCode(500)
        server.enqueue(mockResponse)

        // When
        val service = createTestService()
        val result = service.getTodos()

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertIs<NetworkException>(exception)
        assertEquals(500, exception.httpCode)
    }
}
