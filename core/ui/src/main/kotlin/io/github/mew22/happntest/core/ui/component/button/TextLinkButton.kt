package io.github.mew22.happntest.core.ui.component.button

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import io.github.mew22.happntest.core.ui.designsystem.MainTheme

@Composable
fun TextLinkBlack(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = MainTheme.typography.titleTiny,
) {
    TextLinkButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        textColor = MainTheme.colors.neutrals.black200TextPrimary,
        pressedTextColor = MainTheme.colors.neutrals.black100Pressed,
        textStyle = textStyle,
    )
}

@Composable
fun TextLinkBlue(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = MainTheme.typography.titleTiny,
) {
    TextLinkButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        textColor = MainTheme.colors.blues.blue500Primary,
        pressedTextColor = MainTheme.colors.blues.blue600Pressed,
        textStyle = textStyle,
    )
}

@Suppress("UnnecessaryParentheses")
@Composable
fun TextLinkButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textColor: Color = MainTheme.colors.blues.blue500Primary,
    pressedTextColor: Color = MainTheme.colors.blues.blue600Pressed,
    textStyle: TextStyle = MainTheme.typography.titleTiny,
    indication: Indication? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressedState = interactionSource.collectIsPressedAsState()

    Text(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                onClick = if (enabled) onClick else ({}),
            )
            .testTag("button_text")
            .then(modifier),
        text = text,
        color = if (enabled) {
            if (pressedState.value) {
                pressedTextColor
            } else {
                textColor
            }
        } else {
            MainTheme.colors.neutrals.grey100Disabled
        },
        style = textStyle.copy(textDecoration = TextDecoration.Underline),
    )
}

@Preview
@Composable
private fun DefaultBlack() = MainTheme {
    TextLinkBlack(
        text = "Action",
        onClick = {},
    )
}

@Preview
@Composable
private fun DefaultBlue() = MainTheme {
    TextLinkBlue(
        text = "Action",
        onClick = {},
    )
}

@Preview
@Composable
private fun Disabled() = MainTheme {
    TextLinkButton(
        text = "Action",
        onClick = {},
        enabled = false,
    )
}
