package io.github.mew22.happntest.feature.artlist

import app.cash.turbine.test
import io.github.mew22.happntest.core.common.TestCoroutineStandard
import io.github.mew22.happntest.core.monitoring.LogService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals

class ArtListViewModelTest {
    private val repository: ArtListRepository = mockk()
    private val logService: LogService = mockk()

    private val mockArtList: ArtList = mockk()
    private val mockArtListUi: ArtListUi = mockk()

    private val viewModel by lazy {
        ArtListViewModel(
            artListRepository = repository,
            logService = logService,
        )
    }

    @RegisterExtension
    val testCoroutine = TestCoroutineStandard()

    @Nested
    inner class Initialization {
        @Test
        fun `WHEN viewModel init AND data in cache THEN update state success`() = runTest {
            every {
                repository.artList
            } returns flowOf(listOf(mockArtList))

            mockkStatic(ArtList::toUi) {
                every { mockArtList.toUi() } returns mockArtListUi
                viewModel.state.test {
                    assertEquals(ArtListState(), awaitItem())
                    assertEquals(
                        ArtListState(
                            status = ArtListState.Status.Success(persistentListOf(mockArtListUi))
                        ),
                        awaitItem()
                    )
                }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `WHEN viewModel init AND empty data in cache THEN update call refresh success`() =
            testCoroutine.scope.runTest {
                every {
                    repository.artList
                } returns flowOf(emptyList())

                coEvery {
                    repository.fetchArtList()
                } returns Result.success(listOf(mockArtList))

                viewModel.state.test {
                    assertEquals(ArtListState(), awaitItem())
                }

                testCoroutine.scope.advanceUntilIdle()

                verify(exactly = 1) { repository.artList }
                coVerify(exactly = 1) { repository.fetchArtList() }
            }

        @Test
        fun `WHEN viewModel init AND empty data in cache THEN update call refresh error`() =
            runTest {
                val exception = IllegalStateException()

                every {
                    repository.artList
                } returns flowOf(emptyList())

                coEvery {
                    repository.fetchArtList()
                } returns Result.failure(exception)

                viewModel.state.test {
                    assertEquals(ArtListState(), awaitItem())
                    assertEquals(
                        ArtListState(
                            status = ArtListState.Status.Error
                        ),
                        awaitItem()
                    )
                }

                verify(exactly = 1) { repository.artList }
                coVerify(exactly = 1) { repository.fetchArtList() }
            }
    }

    @Nested
    inner class RefreshRequestedEvent {
        @Test
        fun `WHEN viewModel RefreshRequested THEN perform refresh success`() = runTest {
            every {
                repository.artList
            } returns flowOf(listOf(mockArtList))

            coEvery {
                repository.fetchArtList()
            } returns Result.success(listOf(mockArtList))

            viewModel.dispatch(ArtListEvent.RefreshRequested)

            mockkStatic(ArtList::toUi) {
                every { mockArtList.toUi() } returns mockArtListUi

                viewModel.state.test {
                    // Initial state
                    assertEquals(ArtListState(), awaitItem())
                    assertEquals(
                        ArtListState(
                            status = ArtListState.Status.Success(persistentListOf(mockArtListUi))
                        ),
                        awaitItem()
                    )

                    // Refresh state
                    assertEquals(
                        ArtListState(
                            status = ArtListState.Status.Success(persistentListOf(mockArtListUi)),
                            isRefreshing = true
                        ),
                        awaitItem()
                    )
                    assertEquals(
                        ArtListState(
                            status = ArtListState.Status.Success(persistentListOf(mockArtListUi)),
                            isRefreshing = false
                        ),
                        awaitItem()
                    )
                }

                coVerify(exactly = 1) { repository.fetchArtList() }
            }
        }

        @Test
        fun `WHEN viewModel RefreshRequested THEN perform refresh error`() = runTest {
            val exception = IllegalStateException()

            every {
                repository.artList
            } returns flowOf(listOf(mockArtList))

            coEvery {
                repository.fetchArtList()
            } returns Result.failure(exception)

            viewModel.dispatch(ArtListEvent.RefreshRequested)

            mockkStatic(ArtList::toUi) {
                every { mockArtList.toUi() } returns mockArtListUi

                viewModel.state.test {
                    // Initial state
                    assertEquals(ArtListState(), awaitItem())
                    assertEquals(
                        ArtListState(
                            status = ArtListState.Status.Success(persistentListOf(mockArtListUi))
                        ),
                        awaitItem()
                    )

                    // Refresh state
                    assertEquals(
                        ArtListState(
                            status = ArtListState.Status.Success(persistentListOf(mockArtListUi)),
                            isRefreshing = true
                        ),
                        awaitItem()
                    )
                    assertEquals(
                        ArtListState(
                            status = ArtListState.Status.Error,
                            isRefreshing = false
                        ),
                        awaitItem()
                    )
                }
            }

            coVerify(exactly = 1) { repository.fetchArtList() }
        }
    }

    @Nested
    inner class RetryButtonClickedEvent {
        @Test
        fun `WHEN viewModel RetryButtonClicked THEN perform refresh success`() =
            testCoroutine.scope.runTest {
                every {
                    repository.artList
                } returns flowOf(listOf(mockArtList))

                coEvery {
                    repository.fetchArtList()
                } returns Result.success(listOf(mockArtList))

                viewModel.dispatch(ArtListEvent.RetryButtonClicked)

                mockkStatic(ArtList::toUi) {
                    every { mockArtList.toUi() } returns mockArtListUi
                    viewModel.state.test {
                        // Initial state
                        assertEquals(ArtListState(), awaitItem())
                        assertEquals(
                            ArtListState(
                                status = ArtListState.Status.Success(persistentListOf(mockArtListUi))
                            ),
                            awaitItem()
                        )

                        // Retry state
                        assertEquals(ArtListState(), awaitItem())
                    }
                }

                coVerify(exactly = 1) { repository.fetchArtList() }
            }

        @Test
        fun `WHEN viewModel RetryButtonClicked THEN perform refresh failure`() = runTest {
            val exception = IllegalStateException()

            every {
                repository.artList
            } returns flowOf(listOf(mockArtList))

            coEvery {
                repository.fetchArtList()
            } returns Result.failure(exception)

            viewModel.dispatch(ArtListEvent.RetryButtonClicked)

            mockkStatic(ArtList::toUi) {
                every { mockArtList.toUi() } returns mockArtListUi
                viewModel.state.test {
                    // Initial state
                    assertEquals(ArtListState(), awaitItem())
                    assertEquals(
                        ArtListState(
                            status = ArtListState.Status.Success(persistentListOf(mockArtListUi))
                        ),
                        awaitItem()
                    )

                    // Retry state
                    assertEquals(ArtListState(), awaitItem())
                    assertEquals(
                        ArtListState(
                            status = ArtListState.Status.Error
                        ),
                        awaitItem()
                    )
                }
            }

            coVerify(exactly = 1) { repository.fetchArtList() }
        }
    }
}
