package io.github.mew22.happntest.core.ui.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.mew22.happntest.core.ui.R
import io.github.mew22.happntest.core.ui.designsystem.MainTheme

@Suppress("UnnecessaryParentheses")
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    @DrawableRes iconResId: Int? = null,
    backgroundColor: Color = MainTheme.colors.neutrals.black200TextPrimary,
    contentColor: Color = MainTheme.colors.neutrals.white100Primary,
    border: BorderStroke? = null,
) {
    Button(
        onClick = if (enabled && !loading) onClick else ({}),
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .height(MainTheme.dimens.standard275),
        colors = buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = MainTheme.colors.neutrals.grey100Disabled,
            disabledContentColor = MainTheme.colors.neutrals.white100Primary,
        ),
        contentPadding = PaddingValues(
            vertical = 0.dp,
            horizontal = MainTheme.dimens.standard100
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
        ),
        border = border,
    ) {
        if (loading) {
            ButtonLoadingIndicator(color = contentColor)
        } else {
            if (iconResId != null) {
                ButtonIcon(iconResId = iconResId, iconSize = MainTheme.dimens.standard125)
            }
            ButtonText(
                text = text,
                textStyle = MainTheme.typography.titleMedium,
                color = contentColor,
            )
        }
    }
}

@Preview
@Composable
private fun Default() = MainTheme {
    PrimaryButton(text = "Action", onClick = {})
}

@Preview
@Composable
private fun WithIcon() = MainTheme {
    PrimaryButton(
        text = "Action",
        onClick = {},
        iconResId = R.drawable.ic_close_black
    )
}

@Preview
@Composable
private fun Disabled() = MainTheme {
    PrimaryButton(text = "Action", onClick = {}, enabled = false)
}

@Preview
@Composable
private fun CustomColors() = MainTheme {
    PrimaryButton(
        text = "Action",
        onClick = {},
        backgroundColor = MainTheme.colors.oranges.orange400Primary,
        contentColor = MainTheme.colors.neutrals.black200TextPrimary,
    )
}

@Preview
@Composable
private fun Loading() = MainTheme {
    PrimaryButton(text = "Action", onClick = {}, loading = true)
}
