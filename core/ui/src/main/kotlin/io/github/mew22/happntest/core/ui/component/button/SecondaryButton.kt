package io.github.mew22.happntest.core.ui.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.mew22.happntest.core.ui.R
import io.github.mew22.happntest.core.ui.designsystem.MainTheme

@Suppress("UnnecessaryParentheses")
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    @DrawableRes iconResId: Int? = null,
    contentColor: Color = MainTheme.colors.neutrals.black200TextPrimary,
) {
    OutlinedButton(
        onClick = if (enabled && !loading) onClick else ({}),
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .height(MainTheme.dimens.standard275),
        colors = outlinedButtonColors(
            contentColor = contentColor,
            disabledContentColor = MainTheme.colors.neutrals.grey100Disabled
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) contentColor else MainTheme.colors.neutrals.grey100Disabled,
        ),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = MainTheme.dimens.standard100)
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
                color = if (enabled) contentColor else MainTheme.colors.neutrals.grey100Disabled,
            )
        }
    }
}

@Preview
@Composable
private fun Default() = MainTheme {
    SecondaryButton(text = "Action", onClick = {})
}

@Preview
@Composable
private fun WithIcon() = MainTheme {
    SecondaryButton(
        text = "Action",
        onClick = {},
        iconResId = R.drawable.ic_close_black,
    )
}

@Preview
@Composable
private fun Disabled() = MainTheme {
    SecondaryButton(text = "Action", onClick = {}, enabled = false)
}

@Preview
@Composable
private fun CustomColor() = MainTheme {
    SecondaryButton(
        text = "Action",
        onClick = {},
        contentColor = MainTheme.colors.oranges.orange400Primary,
    )
}

@Preview
@Composable
private fun Loading() = MainTheme {
    SecondaryButton(text = "Action", onClick = {}, loading = true)
}
