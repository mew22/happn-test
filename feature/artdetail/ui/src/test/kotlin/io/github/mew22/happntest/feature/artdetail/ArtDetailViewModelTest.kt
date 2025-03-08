package io.github.mew22.happntest.feature.artdetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.github.mew22.happntest.core.common.TestCoroutineStandard
import io.github.mew22.happntest.core.monitoring.LogService
import io.github.mew22.happntest.feature.artdetail.ArtDetailEvent.RefreshRequested
import io.github.mew22.happntest.feature.artdetail.ArtDetailEvent.RetryButtonClicked
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals

class ArtDetailViewModelTest {
    private val repository: ArtDetailRepository = mockk()
    private val logService: LogService = mockk()
    private val objectNumber = "objectNumber"
    private val savedState = SavedStateHandle(mapOf("objectNumber" to objectNumber))

    private val mockArtDetail: ArtDetail = mockk()
    private val mockArtDetailUi: ArtDetailUi = mockk()

    private val viewModel by lazy {
        ArtDetailViewModel(
            artDetailRepository = repository,
            logService = logService,
            savedStateHandle = savedState,
        )
    }

    @RegisterExtension
    val testCoroutine = TestCoroutineStandard()

    @Nested
    inner class Initialization {
        @Test
        fun `WHEN viewModel init AND data in cache THEN update state success`() = runTest {
            every {
                repository.getDetail(objectNumber)
            } returns flowOf(mockArtDetail)

            mockkStatic(ArtDetail::toUi) {
                every { mockArtDetail.toUi() } returns mockArtDetailUi
                viewModel.state.test {
                    assertEquals(ArtDetailState(), awaitItem())
                    assertEquals(
                        ArtDetailState(
                            status = ArtDetailState.Status.Success(mockArtDetailUi)
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
                    repository.getDetail(objectNumber)
                } returns flowOf(null)

                coEvery {
                    repository.fetchDetail(objectNumber)
                } returns Result.success(mockArtDetail)

                viewModel.state.test {
                    assertEquals(ArtDetailState(), awaitItem())
                }

                testCoroutine.scope.advanceUntilIdle()

                verify(exactly = 1) { repository.getDetail(objectNumber) }
                coVerify(exactly = 1) { repository.fetchDetail(objectNumber) }
            }

        @Test
        fun `WHEN viewModel init AND empty data in cache THEN update call refresh error`() =
            runTest {
                val exception = IllegalStateException()

                every {
                    repository.getDetail(objectNumber)
                } returns flowOf(null)

                coEvery {
                    repository.fetchDetail(objectNumber)
                } returns Result.failure(exception)

                viewModel.state.test {
                    assertEquals(ArtDetailState(), awaitItem())
                    assertEquals(
                        ArtDetailState(
                            status = ArtDetailState.Status.Error
                        ),
                        awaitItem()
                    )
                }

                verify(exactly = 1) { repository.getDetail(objectNumber) }
                coVerify(exactly = 1) { repository.fetchDetail(objectNumber) }
            }
    }

    @Nested
    inner class RefreshRequestedEvent {
        @Test
        fun `WHEN viewModel RefreshRequested THEN perform refresh success`() = runTest {
            every {
                repository.getDetail(objectNumber)
            } returns flowOf(mockArtDetail)

            coEvery {
                repository.fetchDetail(objectNumber)
            } returns Result.success(mockArtDetail)

            viewModel.dispatch(RefreshRequested)

            mockkStatic(ArtDetail::toUi) {
                every { mockArtDetail.toUi() } returns mockArtDetailUi

                viewModel.state.test {
                    // Initial state
                    assertEquals(ArtDetailState(), awaitItem())
                    assertEquals(
                        ArtDetailState(
                            status = ArtDetailState.Status.Success(mockArtDetailUi)
                        ),
                        awaitItem()
                    )

                    // Refresh state
                    assertEquals(
                        ArtDetailState(
                            status = ArtDetailState.Status.Success(mockArtDetailUi),
                            isRefreshing = true
                        ),
                        awaitItem()
                    )
                    assertEquals(
                        ArtDetailState(
                            status = ArtDetailState.Status.Success(mockArtDetailUi),
                            isRefreshing = false
                        ),
                        awaitItem()
                    )
                }

                coVerify(exactly = 1) { repository.fetchDetail(objectNumber) }
            }
        }

        @Test
        fun `WHEN viewModel RefreshRequested THEN perform refresh error`() = runTest {
            val exception = IllegalStateException()

            every {
                repository.getDetail(objectNumber)
            } returns flowOf(mockArtDetail)

            coEvery {
                repository.fetchDetail(objectNumber)
            } returns Result.failure(exception)

            viewModel.dispatch(RefreshRequested)

            mockkStatic(ArtDetail::toUi) {
                every { mockArtDetail.toUi() } returns mockArtDetailUi

                viewModel.state.test {
                    // Initial state
                    assertEquals(ArtDetailState(), awaitItem())
                    assertEquals(
                        ArtDetailState(
                            status = ArtDetailState.Status.Success(mockArtDetailUi)
                        ),
                        awaitItem()
                    )

                    // Refresh state
                    assertEquals(
                        ArtDetailState(
                            status = ArtDetailState.Status.Success(mockArtDetailUi),
                            isRefreshing = true
                        ),
                        awaitItem()
                    )
                    assertEquals(
                        ArtDetailState(
                            status = ArtDetailState.Status.Error,
                            isRefreshing = false
                        ),
                        awaitItem()
                    )
                }
            }

            coVerify(exactly = 1) { repository.fetchDetail(objectNumber) }
        }
    }

    @Nested
    inner class RetryButtonClickedEvent {
        @Test
        fun `WHEN viewModel RetryButtonClicked THEN perform refresh success`() = runTest {
            every {
                repository.getDetail(objectNumber)
            } returns flowOf(mockArtDetail)

            coEvery {
                repository.fetchDetail(objectNumber)
            } returns Result.success(mockArtDetail)

            viewModel.dispatch(RetryButtonClicked)

            mockkStatic(ArtDetail::toUi) {
                every { mockArtDetail.toUi() } returns mockArtDetailUi
                viewModel.state.test {
                    // Initial state
                    assertEquals(ArtDetailState(), awaitItem())
                    assertEquals(
                        ArtDetailState(
                            status = ArtDetailState.Status.Success(mockArtDetailUi)
                        ),
                        awaitItem()
                    )

                    // Retry state
                    assertEquals(ArtDetailState(), awaitItem())
                }
            }

            coVerify(exactly = 1) { repository.fetchDetail(objectNumber) }
        }

        @Test
        fun `WHEN viewModel RetryButtonClicked THEN perform refresh failure`() = runTest {
            val exception = IllegalStateException()

            every {
                repository.getDetail(objectNumber)
            } returns flowOf(mockArtDetail)

            coEvery {
                repository.fetchDetail(objectNumber)
            } returns Result.failure(exception)

            viewModel.dispatch(RetryButtonClicked)

            mockkStatic(ArtDetail::toUi) {
                every { mockArtDetail.toUi() } returns mockArtDetailUi
                viewModel.state.test {
                    // Initial state
                    assertEquals(ArtDetailState(), awaitItem())
                    assertEquals(
                        ArtDetailState(
                            status = ArtDetailState.Status.Success(mockArtDetailUi)
                        ),
                        awaitItem()
                    )

                    // Retry state
                    assertEquals(ArtDetailState(), awaitItem())
                    assertEquals(
                        ArtDetailState(
                            status = ArtDetailState.Status.Error
                        ),
                        awaitItem()
                    )
                }
            }

            coVerify(exactly = 1) { repository.fetchDetail(objectNumber) }
        }
    }
}
