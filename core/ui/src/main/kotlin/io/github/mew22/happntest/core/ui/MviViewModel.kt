package io.github.mew22.happntest.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.mew22.happntest.core.common.runCatchingSafe
import io.github.mew22.happntest.core.common.throttleFirst
import io.github.mew22.happntest.core.monitoring.LogService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Suppress("UnnecessaryAbstractClass")
abstract class MviViewModel<Event, State>(
    defaultState: State,
    protected val logService: LogService,
) : ViewModel() {

    protected val events = MutableSharedFlow<Event>()

    protected val internalState = MutableStateFlow(defaultState)
    val state: StateFlow<State> get() = internalState

    fun dispatch(event: Event) {
        viewModelScope.launch { events.emit(event) }
    }

    protected inline fun <reified SpecificEvent : Event> on(
        crossinline handle: suspend (event: SpecificEvent) -> Unit,
    ) {
        events.filterIsInstance<SpecificEvent>()
            .map { runCatchingSafe { handle(it) } }
            .map { it.exceptionOrNull() }
            .filterNotNull()
            .onEach(this::onError)
            .launchIn(viewModelScope)
    }

    protected inline fun <reified SpecificEvent : Event> onClick(
        crossinline handle: suspend (event: SpecificEvent) -> Unit,
    ) {
        events.filterIsInstance<SpecificEvent>()
            .throttleFirst(CLICK_DEBOUNCE_TIME_MS)
            .map { runCatchingSafe { handle(it) } }
            .map { it.exceptionOrNull() }
            .filterNotNull()
            .onEach(this::onError)
            .launchIn(viewModelScope)
    }

    protected open fun onError(throwable: Throwable) {
        logService.logNonFatalCrash(throwable)
    }

    companion object {
        const val CLICK_DEBOUNCE_TIME_MS = 300L
    }
}
