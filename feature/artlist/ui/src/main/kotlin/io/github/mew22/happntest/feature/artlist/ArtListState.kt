package io.github.mew22.happntest.feature.artlist

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ArtListState(
    val status: Status = Status.Loading,
    val isRefreshing: Boolean = false,
) {
    sealed class Status {
        data object Loading : Status()

        @Immutable
        data class Success(
            val list: ImmutableList<ArtListUi> = persistentListOf(),
        ) : Status()

        data object Error : Status()
    }
}
