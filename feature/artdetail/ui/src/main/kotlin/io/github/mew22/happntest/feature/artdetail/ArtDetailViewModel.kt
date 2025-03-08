package io.github.mew22.happntest.feature.artdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import io.github.mew22.happntest.core.monitoring.LogService
import io.github.mew22.happntest.core.ui.MviViewModel
import io.github.mew22.happntest.feature.artdetail.ArtDetailEvent.RefreshRequested
import io.github.mew22.happntest.feature.artdetail.ArtDetailEvent.RetryButtonClicked
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtDetailViewModel(
    logService: LogService,
    private val artDetailRepository: ArtDetailRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    savedStateHandle: SavedStateHandle,
) : MviViewModel<ArtDetailEvent, ArtDetailState>(ArtDetailState(), logService) {

    private val number: String = checkNotNull(savedStateHandle[OBJECT_NUMBER])

    init {
        observeData()

        on<RetryButtonClicked> {
            internalState.update { state -> state.copy(status = ArtDetailState.Status.Loading) }
            load()
        }
        on<RefreshRequested> {
            internalState.update { state -> state.copy(isRefreshing = true) }
            load()
        }
    }

    private fun load() {
        viewModelScope.launch(ioDispatcher) {
            val newState = artDetailRepository.fetchDetail(number).fold(
                onSuccess = { internalState.value },
                onFailure = {
                    internalState.value.copy(status = ArtDetailState.Status.Error)
                }
            )
            internalState.update { state ->
                newState.copy(isRefreshing = false)
            }
        }
    }

    private fun observeData() {
        artDetailRepository.getDetail(number)
            .flowOn(ioDispatcher)
            .onEach { data ->
                if (data == null) {
                    dispatch(RetryButtonClicked)
                } else {
                    internalState.update { state ->
                        state.copy(
                            status = ArtDetailState.Status.Success(
                                data = data.toUi()
                            )
                        )
                    }
                }
            }
            .catch {
                logService.logNonFatalCrash(it)
            }
            .launchIn(viewModelScope)
    }

    companion object {
        const val OBJECT_NUMBER = "objectNumber"
    }
}
