package io.github.mew22.happntest.feature.artlist

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object ArtListRoute

fun NavController.toArtList(navOptions: NavOptions? = null) {
    navigate(ArtListRoute, navOptions)
}

fun NavGraphBuilder.artListScreen(
    popUp: () -> Unit,
    toDetail: (String) -> Unit,
) {
        composable<ArtListRoute> {
            val viewModel = koinViewModel<ArtListViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            ArtListScreen(
                popUp = popUp,
                toDetail = toDetail,
                state = state,
                dispatch = viewModel::dispatch,
            )
        }
}
