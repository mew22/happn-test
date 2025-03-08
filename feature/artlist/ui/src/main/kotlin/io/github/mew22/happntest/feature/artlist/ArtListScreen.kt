package io.github.mew22.happntest.feature.artlist

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import io.github.mew22.happntest.core.ui.component.ErrorScreen
import io.github.mew22.happntest.core.ui.component.ErrorScreenDefaultActions
import io.github.mew22.happntest.core.ui.component.LoadingScreen
import io.github.mew22.happntest.core.ui.component.NavBar
import io.github.mew22.happntest.core.ui.designsystem.LocalThemeColors
import io.github.mew22.happntest.core.ui.designsystem.LocalThemeDimens
import io.github.mew22.happntest.core.ui.designsystem.LocalThemeTypography
import io.github.mew22.happntest.feature.artlist.ui.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ArtListScreen(
    state: ArtListState,
    dispatch: (ArtListEvent) -> Unit,
    toDetail: (String) -> Unit,
    popUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        targetState = state.status,
        modifier = modifier,
    ) { status ->
        when (status) {
            is ArtListState.Status.Loading -> {
                LoadingScreen()
            }

            is ArtListState.Status.Error -> {
                ErrorScreen(
                    actions = {
                        ErrorScreenDefaultActions(
                            onPrimaryActionClick = { dispatch(ArtListEvent.RetryButtonClicked) },
                            onSecondaryActionClick = { popUp() },
                        )
                    }
                )
            }

            is ArtListState.Status.Success -> {
                ArtListView(
                    list = status.list,
                    isRefreshing = state.isRefreshing,
                    onRefresh = { dispatch(ArtListEvent.RefreshRequested) },
                    toDetail = toDetail,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtListView(
    list: ImmutableList<ArtListUi>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    toDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            NavBar(title = stringResource(R.string.art_list_title))
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(LocalThemeDimens.current.standard100)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard100),
            ) {
                itemsIndexed(list, key = { _, item -> item.id }) { _, art ->
                    ArtListCard(
                        art = art,
                        modifier = Modifier
                            .clickable { toDetail(art.number) }
                    )
                }
            }
        }
    }
}

@Composable
fun ArtListCard(
    art: ArtListUi,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalThemeDimens.current.standard100),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LocalThemeDimens.current.standard100)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalPlatformContext.current)
                    .data(art.imageUrl)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(LocalThemeDimens.current.standard300)
                    .clip(CircleShape),
                error = ColorPainter(LocalThemeColors.current.reds.red400Primary),
            )

            Column {
                Text(art.title, style = LocalThemeTypography.current.titleBig)
                Text(art.artistName, style = LocalThemeTypography.current.bodyMedium)
                Text(art.number, style = LocalThemeTypography.current.bodySmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ArtListPreview() {
    ArtListScreen(
        state = ArtListState(
            status = ArtListState.Status.Success(
                list = persistentListOf(
                    ArtListUi(
                        id = "1",
                        number = "1",
                        artistName = "toto",
                        title = "Florizar",
                        imageUrl = ""
                    ),
                    ArtListUi(
                        id = "2",
                        number = "2",
                        artistName = "pouet",
                        title = "Dracaufeu",
                        imageUrl = ""
                    ),
                    ArtListUi(
                        id = "3",
                        number = "3",
                        artistName = "foo",
                        title = "Tortank",
                        imageUrl = ""
                    ),
                )
            )
        ),
        dispatch = {},
        toDetail = {},
        popUp = {},
    )
}
