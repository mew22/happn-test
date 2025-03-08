package io.github.mew22.happntest.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.mew22.happntest.core.ui.R
import io.github.mew22.happntest.core.ui.component.NavBar.Action
import io.github.mew22.happntest.core.ui.component.button.TextLinkBlue
import io.github.mew22.happntest.core.ui.designsystem.MainTheme

const val TAG_NAV_BAR_TITLE = "toolbar_title"
const val TAG_NAV_BAR_SECONDARY_INFO = "toolbar_secondary_info"

object NavBar {
    enum class SecondaryInfoLocation {
        ABOVE_TITLE,
        UNDER_TITLE;

        val isAboveTitle get() = this == ABOVE_TITLE

        val isUnderTitle get() = this == UNDER_TITLE
    }

    sealed class Action(open val onClick: () -> Unit) {
        data class IconAction(
            val icon: Painter,
            override val onClick: () -> Unit = {},
            val contentDescription: String? = null,
            val tint: Color? = null,
        ) : Action(onClick)

        data class TextAction(
            val text: String,
            override val onClick: () -> Unit = {},
        ) : Action(onClick)
    }
}

@Composable
fun DefaultBackAction(onClick: () -> Unit) = Action.IconAction(
    icon = painterResource(id = R.drawable.ic_back),
    onClick = onClick,
    contentDescription = stringResource(R.string.common_navigate_up)
)

@Composable
fun DefaultCloseAction(onClick: () -> Unit) = Action.IconAction(
    icon = painterResource(id = R.drawable.ic_close_black),
    onClick = onClick,
    contentDescription = stringResource(R.string.common_navigate_up),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    secondaryInfo: String? = null,
    secondaryInfoLocation: NavBar.SecondaryInfoLocation = NavBar.SecondaryInfoLocation.UNDER_TITLE,
    navigationAction: NavBar.Action? = null,
    firstAction: NavBar.Action? = null,
    secondAction: NavBar.Action? = null,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MainTheme.colors.neutrals.white100Primary),
        modifier = modifier,
        title = {
            Column {
                if (secondaryInfo != null && secondaryInfoLocation.isAboveTitle) {
                    ToolbarSecondaryInfo(text = secondaryInfo)
                }
                if (title != null) {
                    ToolbarTitle(text = title, secondaryInfo?.let { MainTheme.typography.titleMedium })
                }
                if (secondaryInfo != null && secondaryInfoLocation.isUnderTitle) {
                    ToolbarSecondaryInfo(text = secondaryInfo)
                }
            }
        },
        navigationIcon = {
            if (navigationAction != null) {
                ToolbarAction(action = navigationAction)
                Spacer(modifier = Modifier.width(MainTheme.dimens.standard25))
            } else {
                Spacer(modifier = Modifier.width(MainTheme.dimens.standard75))
            }
        },
        actions = {
            if (firstAction != null) {
                ToolbarAction(action = firstAction)
            }
            if (secondAction != null) {
                ToolbarAction(action = secondAction)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBarWithCustomEndView(
    modifier: Modifier = Modifier,
    title: String? = null,
    secondaryInfo: String? = null,
    secondaryInfoLocation: NavBar.SecondaryInfoLocation = NavBar.SecondaryInfoLocation.UNDER_TITLE,
    navigationAction: NavBar.Action? = null,
    customView: @Composable (() -> Unit)? = null,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MainTheme.colors.neutrals.white100Primary),
        modifier = modifier,
        title = {
            Column {
                if (secondaryInfo != null && secondaryInfoLocation.isAboveTitle) {
                    ToolbarSecondaryInfo(text = secondaryInfo)
                }
                if (title != null) {
                    ToolbarTitle(text = title, secondaryInfo?.let { MainTheme.typography.titleMedium })
                }
                if (secondaryInfo != null && secondaryInfoLocation.isUnderTitle) {
                    ToolbarSecondaryInfo(text = secondaryInfo)
                }
            }
        },
        navigationIcon = {
            if (navigationAction != null) {
                ToolbarAction(action = navigationAction)
                Spacer(modifier = Modifier.width(4.dp))
            } else {
                Spacer(modifier = Modifier.width(12.dp))
            }
        },
        actions = {
            customView?.invoke()
        }
    )
}

@Composable
private fun ToolbarTitle(text: String, style: TextStyle? = null) {
    Text(
        text = text,
        style = style ?: MainTheme.typography.titleBig,
        modifier = Modifier.testTag(TAG_NAV_BAR_TITLE)
    )
}

@Composable
private fun ToolbarSecondaryInfo(text: String) {
    Text(
        text = text,
        style = MainTheme.typography.bodyTiny,
        modifier = Modifier.testTag(TAG_NAV_BAR_SECONDARY_INFO)
    )
}

@Composable
private fun ToolbarAction(action: NavBar.Action) {
    Box {
        when (action) {
            is NavBar.Action.IconAction -> IconButton(onClick = action.onClick) {
                Icon(
                    painter = action.icon,
                    contentDescription = action.contentDescription,
                    tint = action.tint ?: LocalContentColor.current
                )
            }
            is NavBar.Action.TextAction -> TextLinkBlue(
                text = action.text,
                onClick = action.onClick,
                modifier = Modifier.padding(end = MainTheme.dimens.standard100)
            )
        }
    }
}

@Preview
@Composable
private fun Navigation_Title() {
    NavBar(
        title = "Title goes here",
        navigationAction = NavBar.Action.IconAction(
            icon = painterResource(id = R.drawable.ic_back)
        )
    )
}

@Preview
@Composable
private fun Navigation_NoTitle() {
    NavBar(
        navigationAction = NavBar.Action.IconAction(
            icon = painterResource(id = R.drawable.ic_back)
        )
    )
}

@Preview
@Composable
private fun Navigation_Title_SecondaryInfoUnderTitle() {
    NavBar(
        title = "Primary title here",
        secondaryInfo = "Secondary info here",
        navigationAction = NavBar.Action.IconAction(
            icon = painterResource(id = R.drawable.ic_back)
        )
    )
}

@Preview
@Composable
private fun Navigation_Title_SecondaryInfoAboveTitle() {
    NavBar(
        title = "Title goes here",
        secondaryInfo = "Secondary info here",
        secondaryInfoLocation = NavBar.SecondaryInfoLocation.ABOVE_TITLE,
        navigationAction = NavBar.Action.IconAction(
            icon = painterResource(id = R.drawable.ic_back)
        ),
    )
}

@Preview
@Composable
private fun Empty() {
    NavBar()
}

@Preview
@Composable
private fun Title() {
    NavBar(title = "Title goes here")
}

@Preview
@Composable
private fun Title_OneAction() {
    NavBar(
        title = "Title goes here",
        firstAction = NavBar.Action.IconAction(
            icon = painterResource(id = R.drawable.ic_close_black)
        )
    )
}

@Preview
@Composable
private fun Navigation_Title_TwoActions() {
    NavBar(
        title = "Title goes here",
        navigationAction = NavBar.Action.IconAction(
            icon = painterResource(id = R.drawable.ic_back)
        ),
        firstAction = NavBar.Action.IconAction(
            icon = painterResource(id = R.drawable.ic_close_black)
        ),
        secondAction = NavBar.Action.IconAction(
            icon = painterResource(id = R.drawable.ic_close_black)
        ),
    )
}
