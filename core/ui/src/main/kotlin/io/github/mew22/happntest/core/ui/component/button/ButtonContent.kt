package io.github.mew22.happntest.core.ui.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import io.github.mew22.happntest.core.ui.designsystem.MainTheme

@Composable
fun ButtonLoadingIndicator(color: Color, modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        color = color,
        strokeWidth = MainTheme.dimens.standard10,
        modifier = modifier
            .height(MainTheme.dimens.standard100)
            .width(MainTheme.dimens.standard100)
            .testTag("button_loading"),
    )
}

@Composable
internal fun ButtonIcon(@DrawableRes iconResId: Int, iconSize: Dp) {
    Box(modifier = Modifier.padding(end = MainTheme.dimens.standard50)) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier
                .size(iconSize)
                .testTag("button_icon")
        )
    }
}

@Composable
internal fun ButtonText(text: String, textStyle: TextStyle, color: Color) {
    Text(
        text = text,
        style = textStyle,
        color = color,
        textAlign = TextAlign.Center,
        modifier = Modifier.testTag("button_text")
    )
}
