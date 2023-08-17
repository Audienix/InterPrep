package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun FullScreenEmptyState(
    modifier: Modifier = Modifier,
    imageId: Int,
    emptyStateTitle: String = "",
    emptyStateText: String = ""
) {
    Box {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.dimension_8dp),
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                //TODO change constant height value
                modifier = Modifier
                    .height(240.dp)
                    .width(290.dp),
                painter = painterResource(id = imageId),
                contentDescription = "No data"
            )
            Text(
                text = emptyStateTitle,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialColorPalette.onSurface,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = emptyStateText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialColorPalette.onSurfaceVariant,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FullScreenEmptyStateInterview() {
    FullScreenEmptyState(
        Modifier,
        R.drawable.empty_state_dashboard,
        stringResource(id = R.string.empty_state_title_dashboard),
        stringResource(id = R.string.empty_state_description_dashboard)
    )
}

@Preview(showBackground = true)
@Composable
fun FullScreenEmptyStateResource() {
    FullScreenEmptyState(
        Modifier,
        R.drawable.empty_state_resource,
        stringResource(id = R.string.empty_state_title_resource),
        stringResource(id = R.string.empty_state_description_resource)
    )
}

@Preview(showBackground = true)
@Composable
fun FullScreenEmptyStateNotes() {
    FullScreenEmptyState(
        Modifier,
        R.drawable.empty_state_notes,
        stringResource(id = R.string.empty_state_title_note),
        stringResource(id = R.string.empty_state_description_note)
    )
}
