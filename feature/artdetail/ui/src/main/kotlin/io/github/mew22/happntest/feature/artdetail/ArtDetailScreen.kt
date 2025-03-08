package io.github.mew22.happntest.feature.artdetail

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import io.github.mew22.happntest.core.ui.component.DefaultBackAction
import io.github.mew22.happntest.core.ui.component.ErrorScreen
import io.github.mew22.happntest.core.ui.component.ErrorScreenDefaultActions
import io.github.mew22.happntest.core.ui.component.LoadingScreen
import io.github.mew22.happntest.core.ui.component.NavBar
import io.github.mew22.happntest.core.ui.component.ScrollableContent
import io.github.mew22.happntest.core.ui.designsystem.LocalThemeColors
import io.github.mew22.happntest.core.ui.designsystem.LocalThemeDimens
import io.github.mew22.happntest.core.ui.designsystem.LocalThemeTypography
import io.github.mew22.happntest.feature.artdetail.ArtDetailState.Status
import io.github.mew22.happntest.feature.artdetail.ui.R

@Composable
fun ArtDetailScreen(
    state: ArtDetailState,
    dispatch: (ArtDetailEvent) -> Unit,
    popUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        targetState = state.status,
        modifier = modifier,
    ) { status ->
        when (status) {
            is Status.Loading -> {
                LoadingScreen()
            }

            is Status.Error -> {
                ErrorScreen(
                    actions = {
                        ErrorScreenDefaultActions(
                            onPrimaryActionClick = { dispatch(ArtDetailEvent.RetryButtonClicked) },
                            onSecondaryActionClick = { popUp() },
                        )
                    }
                )
            }

            is Status.Success -> {
                ArtDetailView(
                    data = status.data,
                    isRefreshing = state.isRefreshing,
                    onRefresh = { dispatch(ArtDetailEvent.RefreshRequested) },
                    popUp = popUp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtDetailView(
    data: ArtDetailUi,
    popUp: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            NavBar(
                title = data.title,
                navigationAction = DefaultBackAction(popUp)
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        ArtDetailCard(
            data = data,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ArtDetailCard(
    data: ArtDetailUi,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh
            ) {
                ScrollableContent(
                    verticalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard100),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                data.colors.firstOrNull()
                                    ?: LocalThemeColors.current.oranges.orange300Pastel
                            )
                            .padding(vertical = LocalThemeDimens.current.standard100),
                        contentAlignment = Alignment.Center,
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalPlatformContext.current)
                                .data(data.imageUrl)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(LocalThemeDimens.current.standard1000)
                                .border(
                                    LocalThemeDimens.current.standard25,
                                    LocalThemeColors.current.neutrals.grey400ViewBorder,
                                    CircleShape
                                )
                                .clip(CircleShape),
                            error = ColorPainter(LocalThemeColors.current.reds.red400Primary),
                        )
                    }
                    Column(
                        modifier = Modifier.padding(LocalThemeDimens.current.standard100),
                        verticalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard100),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard100),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(stringResource(R.string.title_name))
                            Text(data.title, style = LocalThemeTypography.current.titleBig)
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard100),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(stringResource(R.string.id_name))
                            Text(data.number, style = LocalThemeTypography.current.bodySmall)
                        }
                        if (data.objectTypes.isNotEmpty()) {
                            ShowObjectTypes(objectTypes = data.objectTypes)
                        }
                        if (data.objectCollections.isNotEmpty()) {
                            ShowObjectCollections(objectCollections = data.objectCollections)
                        }
                        if (data.artists.isNotEmpty()) {
                            ShowArtists(artists = data.artists)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColumnScope.ShowArtists(artists: List<ArtistUi>, modifier: Modifier = Modifier) {
    Text(text = stringResource(R.string.artist_name))
    FlowRow(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth(1f)
            .padding(LocalThemeDimens.current.standard100),
        verticalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard25),
        horizontalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard50),
    ) {
        artists.forEach {
            FilterChip(
                selected = false,
                onClick = {},
                label = {
                    Text(
                        text = "${it.name}, ${it.nationality}",
                        style = LocalThemeTypography.current.bodySmall
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColumnScope.ShowObjectTypes(objectTypes: List<String>, modifier: Modifier = Modifier) {
    Text(text = stringResource(R.string.type_name))
    FlowRow(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth(1f)
            .padding(LocalThemeDimens.current.standard100),
        verticalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard25),
        horizontalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard50),
    ) {
        objectTypes.forEach {
            FilterChip(
                selected = false,
                onClick = {},
                label = { Text(text = it, style = LocalThemeTypography.current.bodySmall) },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColumnScope.ShowObjectCollections(
    objectCollections: List<String>,
    modifier: Modifier = Modifier
) {
    Text(text = stringResource(R.string.collection_name))
    FlowRow(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth(1f)
            .padding(LocalThemeDimens.current.standard100),
        verticalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard25),
        horizontalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard50),
    ) {
        objectCollections.forEach {
            FilterChip(
                selected = false,
                onClick = {},
                label = { Text(text = it, style = LocalThemeTypography.current.bodySmall) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArtDetailPreview() {
    ArtDetailScreen(
        state = ArtDetailState(
            status = Status.Success(
                data = ArtDetailUi(),
            )
        ),
        dispatch = {},
        popUp = {},
    )
}
