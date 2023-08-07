package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun IPDescriptionRow(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    icon: Int,
    contentDescription: String
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Row {
            Icon(
                painterResource(id = icon),
                contentDescription = contentDescription,
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.dimension_8dp))
                    .size(dimensionResource(id = R.dimen.dimension_24dp)),
                tint = MaterialColorPalette.primary
            )
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(R.dimen.dimension_8dp),
                    Alignment.Top
                ),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialColorPalette.onSurface

                )
                Text(
                    text = body, style = MaterialTheme.typography.bodySmall,
                    color = MaterialColorPalette.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
fun IPIntroductionRow1Preview() {
    IPDescriptionRow(
        title = stringResource(id = R.string.dashboard_title),
        body = stringResource(id = R.string.dashboard_description),
        icon = R.drawable.ic_dashboard_24,
        contentDescription = "dashboard_icon"
    )
}

@Preview
@Composable
fun IPIntroductionRow2Preview() {
    IPDescriptionRow(
        title = stringResource(id = R.string.note_title),
        body = stringResource(id = R.string.note_description),
        icon = R.drawable.ic_note_24,
        contentDescription = "note_icon"
    )
}

@Preview
@Composable
fun IPIntroductionRow3Preview() {
    IPDescriptionRow(
        title = stringResource(id = R.string.resource_title),
        body = stringResource(id = R.string.resource_description),
        icon = R.drawable.ic_resource_24,
        contentDescription = "resource_icon"
    )
}
