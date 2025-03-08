package io.github.mew22.happntest.feature.artlist

sealed class ArtListEvent {
    data object RetryButtonClicked : ArtListEvent()
    data object RefreshRequested : ArtListEvent()
}
