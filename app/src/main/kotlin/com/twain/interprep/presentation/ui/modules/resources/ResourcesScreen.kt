package com.twain.interprep.presentation.ui.modules.resources

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.twain.interprep.R
import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.presentation.ui.components.generic.FullScreenEmptyState
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPFAB
import com.twain.interprep.presentation.ui.components.resource.ResourceCard

@Composable
fun ResourcesScreen(
    viewModel: ResourcesViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            topBar = { IPAppBar(stringResource(id = R.string.nav_item_resources)) },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                IPFAB(onFABClick = {/* TODO */ })
            },
            content = { padding ->
                ShowResourcesScreenContent(viewModel, padding)
            }
        )
    }
}

@Composable
private fun ShowResourcesScreenContent(
    viewModel: ResourcesViewModel = hiltViewModel(),
    padding: PaddingValues,
) {
    LaunchedEffect(Unit) {
        viewModel.getResourceAndLinksPair()
    }

    if (viewModel.resourceAndLinksPair is ViewResult.UnInitialized) {
        FullScreenEmptyState(
            Modifier,
            R.drawable.empty_state_resource,
            stringResource(id = R.string.empty_state_title_resource),
            stringResource(id = R.string.empty_state_description_resource)
        )
    } else if (viewModel.resourceAndLinksPair is ViewResult.Loaded) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.dimension_16dp)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_16dp))
        ) {
            val resourceAndLinks =
                (viewModel.resourceAndLinksPair as
                    ViewResult.Loaded<List<Pair<Resource, List<ResourceLink>>>>).data
            items(resourceAndLinks) {(resource, links) ->
                ResourceCard(
                    resourceAndLinks = resource to links,
                    onEditResourceClick = {/* TODO */ }
                )
            }
        }
    }
}
