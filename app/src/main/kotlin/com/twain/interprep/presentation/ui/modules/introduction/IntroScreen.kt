package com.twain.interprep.presentation.ui.modules.introduction


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.components.generic.IPDescriptionRow
import com.twain.interprep.presentation.ui.components.generic.IPFilledButton
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.SetStatusBarColor

@Composable
fun IntroScreen(onGetStartedClicked: () -> Unit) {
    InterPrepTheme {
        SetStatusBarColor(statusBarColor = MaterialColorPalette.surface)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.dimension_16dp),
                    vertical = dimensionResource(id = R.dimen.dimension_24dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_24dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_welcome),
                    contentDescription = "Intro Screen Image"
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dimension_icon_size_48)),
                        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                        contentDescription = stringResource(id = R.string.app_name),
                    )
                    IPHeader(
                        modifier = Modifier.wrapContentWidth(),
                        text = stringResource(id = R.string.app_name),
                        textStyle = typography.headlineMedium,
                        alignTextToCenter = true
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimension_24dp)),
                    horizontalAlignment = Alignment.Start
                ) {
                    IPDescriptionRow(
                        title = stringResource(id = R.string.intro_dashboard_title),
                        body = stringResource(id = R.string.intro_dashboard_description),
                        icon = R.drawable.ic_dashboard_24,
                        contentDescription = "dashboard_icon"
                    )
                    IPDescriptionRow(
                        title = stringResource(id = R.string.intro_note_title),
                        body = stringResource(id = R.string.intro_note_description),
                        icon = R.drawable.ic_note_24,
                        contentDescription = "note_icon"
                    )
                    IPDescriptionRow(
                        title = stringResource(id = R.string.intro_resource_title),
                        body = stringResource(id = R.string.intro_resource_description),
                        icon = R.drawable.ic_resource_24,
                        contentDescription = "resource_icon"
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_16dp)))
            IPFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp)),
                text = stringResource(id = R.string.button_get_started),
                textStyle = typography.bodyLarge,
                onClick = onGetStartedClicked
            )
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun IntroScreenPreview() {
    InterPrepTheme {
        IntroScreen {}
    }

}
