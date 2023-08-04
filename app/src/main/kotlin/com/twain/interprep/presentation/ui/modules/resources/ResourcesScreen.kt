package com.twain.interprep.presentation.ui.modules.resources

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
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.data.model.Resource
import com.twain.interprep.data.model.ResourceLink
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.FullScreenEmptyState
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPFAB
import com.twain.interprep.presentation.ui.components.resource.ResourceCard
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun ResourcesScreen(
    navController: NavHostController,
    viewModel: ResourcesViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = { IPAppBar(stringResource(id = R.string.nav_item_resources)) },
            containerColor = MaterialColorPalette.background,
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                IPFAB {
                    navController.navigate(AppScreens.AddResource.withArgs(0)) {
                        popUpTo(AppScreens.Resources.route)
                    }
                }
            },
            content = { padding ->
                ShowResourcesScreenContent(navController, viewModel, padding)
            }
        )
    }
}

@Composable
private fun ShowResourcesScreenContent(
    navController: NavHostController,
    viewModel: ResourcesViewModel = hiltViewModel(),
    padding: PaddingValues,
) {
    LaunchedEffect(Unit) {
        viewModel.getResourceAndLinksPair()
    }

    if (viewModel.resourceAndLinksPair is ViewResult.Loaded) {
        val resourceAndLinks =
            (viewModel.resourceAndLinksPair as
                ViewResult.Loaded<List<Pair<Resource, List<ResourceLink>>>>).data
        if (resourceAndLinks.isEmpty()) {
            FullScreenEmptyState(
                Modifier,
                R.drawable.empty_state_resource,
                stringResource(id = R.string.empty_state_title_resource),
                stringResource(id = R.string.empty_state_description_resource)
            )
        } else {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.dimension_16dp)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_16dp))
            ) {
                items(resourceAndLinks) { (resource, links) ->
                    ResourceCard(
                        resourceAndLinks = resource to links,
                        onEditResourceClick = {
                            navController.navigate(
                                AppScreens.AddResource.withArgs(resource.resourceId)
                            )
                        }
                    )
                }
            }
        }
    }
}
