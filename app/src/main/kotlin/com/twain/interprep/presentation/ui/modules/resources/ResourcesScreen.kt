package com.twain.interprep.presentation.ui.modules.resources

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.FullScreenEmptyState

@Composable
fun ResourcesScreen(
    viewModel: ResourcesViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        IPAppBar(stringResource(id = R.string.nav_item_resources)){}
        FullScreenEmptyState(
            Modifier,
            R.drawable.empty_state_resource,
            stringResource(id = R.string.empty_state_title_resource),
            stringResource(id = R.string.empty_state_description_resource)
        )
    }
}
