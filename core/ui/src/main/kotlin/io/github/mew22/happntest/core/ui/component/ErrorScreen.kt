package io.github.mew22.happntest.core.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.github.mew22.happntest.core.ui.R
import io.github.mew22.happntest.core.ui.component.button.PrimaryButton
import io.github.mew22.happntest.core.ui.component.button.SecondaryButton
import io.github.mew22.happntest.core.ui.designsystem.MainTheme

const val CROSS_ICON_CONTENT_DESCRIPTION = "crossIconContentDescription"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.common_error_title),
    message: String = stringResource(id = R.string.common_error_message),
    image: @Composable () -> Unit = { ErrorScreenDefaultImage() },
    actions: @Composable () -> Unit = { },
    crossIconAction: (() -> Unit)? = null,
) {
    Scaffold(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MainTheme.dimens.standard100),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (crossIconAction != null) {
                Image(
                    painter = painterResource(id = R.drawable.ic_close_black),
                    contentDescription = CROSS_ICON_CONTENT_DESCRIPTION,
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(top = MainTheme.dimens.standard150)
                        .clickable { crossIconAction() },
                )
            }
            Column(
                modifier = Modifier
                    .padding(bottom = MainTheme.dimens.standard300)
                    .weight(1F)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    image()
                }
                Spacer(modifier = Modifier.height(MainTheme.dimens.standard250))
                ErrorTitle(title)
                Spacer(modifier = Modifier.height(MainTheme.dimens.standard100))
                ErrorMessage(message)
            }
            actions()
        }
    }
}

@Composable
fun ErrorScreenDefaultImage(modifier: Modifier = Modifier,) {
    Image(
        painter = painterResource(R.drawable.warning_error),
        contentDescription = null,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
fun ErrorScreenDefaultActions(
    onPrimaryActionClick: () -> Unit,
    onSecondaryActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryActionText: String = stringResource(id = R.string.common_try_again),
    secondaryActionText: String = stringResource(id = R.string.common_back_home),
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Box(modifier = Modifier.weight(1f)) {
            SecondaryButton(text = secondaryActionText, onClick = onSecondaryActionClick)
        }
        Spacer(modifier = Modifier.width(MainTheme.dimens.standard50))
        Box(modifier = Modifier.weight(1f)) {
            PrimaryButton(text = primaryActionText, onClick = onPrimaryActionClick)
        }
    }
}

@Composable
private fun ErrorTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MainTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
private fun ErrorMessage(message: String) {
    Text(text = message, style = MainTheme.typography.bodyMedium, textAlign = TextAlign.Center)
}

@Preview
@Composable
private fun Default() {
    ErrorScreen()
}

@Preview
@Composable
private fun DefaultWithCrossIcon() {
    ErrorScreen(crossIconAction = {})
}

@Preview
@Composable
private fun DefaultWithAction() {
    ErrorScreen(crossIconAction = {}, actions = {
        ErrorScreenDefaultActions(onPrimaryActionClick = {}, onSecondaryActionClick = {})
    })
}
