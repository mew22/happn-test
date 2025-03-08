package io.github.mew22.happntest.feature.artdetail

import androidx.compose.runtime.Immutable

@Immutable
data class ArtDetailState(
    val status: Status = Status.Loading,
    val isRefreshing: Boolean = false,
) {
    sealed class Status {
        data object Loading : Status()

        @Immutable
        data class Success(
            val data: ArtDetailUi,
        ) : Status()

        data object Error : Status()
    }
}
