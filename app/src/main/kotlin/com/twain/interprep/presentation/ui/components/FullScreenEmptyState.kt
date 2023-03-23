package com.twain.interprep.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twain.interprep.R

@Composable
fun FullScreenEmptyState(modifier: Modifier,
                         imageId: Int,
                         emptyStateTitle: String = "",
                         emptyStateText: String = "") {
    Box {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                // dimensionResource(R.dimen.padding_medium),
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "Full Screen Empty State Image"
            )
            Text(text = emptyStateTitle, style = MaterialTheme.typography.bodyLarge)
            Text(text = emptyStateText, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FullScreenEmptyStatePreview() {
    FullScreenEmptyState(
        Modifier,
        R.drawable.empty_state_dashboard,
        stringResource(id = R.string.dashboard_empty_title),
        stringResource(id = R.string.dashboard_empty_text)
    )
}