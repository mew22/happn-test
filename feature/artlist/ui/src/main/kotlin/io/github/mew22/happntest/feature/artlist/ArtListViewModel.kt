package io.github.mew22.happntest.feature.artlist

import androidx.lifecycle.viewModelScope
import io.github.mew22.happntest.core.monitoring.LogService
import io.github.mew22.happntest.core.ui.MviViewModel
import io.github.mew22.happntest.feature.artlist.ArtListEvent.RefreshRequested
import io.github.mew22.happntest.feature.artlist.ArtListEvent.RetryButtonClicked
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtListViewModel(
    logService: LogService,
    private val artListRepository: ArtListRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MviViewModel<ArtListEvent, ArtListState>(ArtListState(), logService) {

    init {
        observeData()

        on<RetryButtonClicked> {
            internalState.update { state -> state.copy(status = ArtListState.Status.Loading) }
            load()
        }
        on<RefreshRequested> {
            internalState.update { state -> state.copy(isRefreshing = true) }
            load()
        }
    }

    private fun load() {
        viewModelScope.launch(ioDispatcher) {
            val newState = artListRepository.fetchArtList()
                .fold(
                    onSuccess = { internalState.value },
                    onFailure = {
                        internalState.value.copy(status = ArtListState.Status.Error)
                    }
                )
            internalState.update { newState.copy(isRefreshing = false) }
        }
    }

    private fun observeData() {
        artListRepository.artList
            .flowOn(ioDispatcher)
            .onEach { data ->
                if (data.isEmpty()) {
                    dispatch(RetryButtonClicked)
                } else {
                    internalState.update { state ->
                        state.copy(
                            status = ArtListState.Status.Success(
                                list = data.map { it.toUi() }.toImmutableList()
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
}
