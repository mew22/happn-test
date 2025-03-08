package io.github.mew22.happntest.feature.artdetail

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class ArtDetailRoute(val objectNumber: String)

fun NavController.toArtDetail(objectNumber: String, navOptions: NavOptions? = null) {
    navigate(ArtDetailRoute(objectNumber), navOptions)
}

fun NavGraphBuilder.artDetailScreen(
    popUp: () -> Unit,
) {
    composable<ArtDetailRoute> {
        val viewModel = koinViewModel<ArtDetailViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        ArtDetailScreen(
            popUp = popUp,
            state = state,
            dispatch = viewModel::dispatch,
        )
    }
}
