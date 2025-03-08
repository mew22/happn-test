package io.github.mew22.happntest.core.network

import io.github.mew22.happntest.core.environment.EnvironmentGateway
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock

internal class NetworkTestRule : TestWatcher() {

    val server = MockWebServer()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(networkModule)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    val environmentGateway: EnvironmentGateway by lazy {
        koinTestRule.koin.declareMock<EnvironmentGateway> {
            every { isDebug } returns false
            every { isMock } returns false
            every { androidVersion } returns 27
        }
    }

    inline fun <reified T> getMockService(): T = koinTestRule.koin.get<NetworkBuilder>().build().create<T> { mockk() }

    override fun starting(description: Description) {
        server.start()

        koinTestRule.koin.declareMock<Api> {
            every { getApi() } returns server.url("/v1/test/").toString()
        }
    }

    override fun finished(description: Description) {
        server.shutdown()
    }
}
