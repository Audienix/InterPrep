package com.twain.interprep.presentation.ui.modules.introduction


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.components.generic.IPDescriptionRow
import com.twain.interprep.presentation.ui.components.generic.IPFilledButton
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import com.twain.interprep.presentation.ui.theme.PurpleButton

@Composable
fun IntroScreen(onGetStartedClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dimension_32dp))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_16dp))
    ) {
        Image(
            modifier = Modifier
                .height(dimensionResource(R.dimen.dimension_205dp))
                .width(dimensionResource(R.dimen.dimension_200dp)),
            painter = painterResource(id = R.drawable.intro_screen_image),
            contentDescription = "Intro Screen Image"
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_32dp)))
        IPHeader(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.intro_screen_heading),
            textStyle = typography.headlineLarge,
            fontWeight = FontWeight.Medium,
            alignTextToCenter = true

        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_24dp)))
        IPDescriptionRow(
            title = stringResource(id = R.string.dashboard_title),
            body = stringResource(id = R.string.dashboard_description),
            icon = R.drawable.ic_dashboard_24,
            contentDescription = "dashboard_icon"
        )
        IPDescriptionRow(
            title = stringResource(id = R.string.note_title),
            body = stringResource(id = R.string.note_description),
            icon = R.drawable.ic_note_24,
            contentDescription = "note_icon"
        )
        IPDescriptionRow(
            title = stringResource(id = R.string.resource_title),
            body = stringResource(id = R.string.resource_description),
            icon = R.drawable.ic_resource_24,
            contentDescription = "resource_icon"
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_44dp)))
        IPFilledButton(
            text = stringResource(id = R.string.get_started_button_text),
            textColor = Color.White,
            iconColor = PurpleButton,
            backgroundColor = PurpleButton,
            disabledContentColor = PurpleButton,
            textStyle = typography.bodyLarge,
            onClick = onGetStartedClicked,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun IntroScreenPreview() {
    InterPrepTheme {
        IntroScreen{}
    }

}