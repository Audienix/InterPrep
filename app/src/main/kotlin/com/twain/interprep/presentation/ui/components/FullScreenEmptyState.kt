package com.twain.interprep.presentation.ui.components

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twain.interprep.R

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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.dimension_16dp),
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .height(240.dp)
                    .width(290.dp),
                painter = painterResource(id = imageId),
                contentDescription = "Full Screen Empty State Image"
            )
            Text(text = emptyStateTitle, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = emptyStateText,
                style = MaterialTheme.typography.bodyMedium,
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
        stringResource(id = R.string.dashboard_empty_title),
        stringResource(id = R.string.dashboard_empty_text)
    )
}

@Preview(showBackground = true)
@Composable
fun FullScreenEmptyStateResource() {
    FullScreenEmptyState(
        Modifier,
        R.drawable.empty_state_resource,
        stringResource(id = R.string.dashboard_empty_resource),
        stringResource(id = R.string.dashboard_empty_resource_text)
    )
}

@Preview(showBackground = true)
@Composable
fun FullScreenEmptyStateNotes() {
    FullScreenEmptyState(
        Modifier,
        R.drawable.empty_state_notes,
        stringResource(id = R.string.dashboard_empty_note),
        stringResource(id = R.string.dashboard_empty_note_text)
    )
}
