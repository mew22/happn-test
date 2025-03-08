package io.github.mew22.happntest

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.github.mew22.happntest.feature.artdetail.artDetailScreen
import io.github.mew22.happntest.feature.artdetail.toArtDetail
import io.github.mew22.happntest.feature.artlist.ArtListRoute
import io.github.mew22.happntest.feature.artlist.artListScreen
import kotlin.reflect.KClass

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: KClass<*> = ArtListRoute::class,
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        artListScreen(
            toDetail = navController::toArtDetail,
            popUp = navController::navigateUp
        )
        artDetailScreen(
            popUp = navController::navigateUp
        )
    }
}
