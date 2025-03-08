package io.github.mew22.happntest.feature.artdetail

sealed class ArtDetailEvent {
    data object RetryButtonClicked : ArtDetailEvent()
    data object RefreshRequested : ArtDetailEvent()
}
