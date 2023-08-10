package com.twain.interprep.presentation.ui.modules.resources

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = { IPAppBar(stringResource(id = R.string.nav_item_resources)) },
            containerColor = MaterialColorPalette.surface,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowResourcesScreenContent(
    navController: NavHostController,
    viewModel: ResourcesViewModel = hiltViewModel(),
    padding: PaddingValues,
) {
    var active by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getSearchingResult(viewModel.searchText)
    }

    if (viewModel.resourceAndLinksPairs is ViewResult.Loaded) {
        val resourceAndLinks =
            (viewModel.resourceAndLinksPairs as
                ViewResult.Loaded<List<Pair<Resource, List<ResourceLink>>>>).data
        if (resourceAndLinks.isEmpty()) {
            FullScreenEmptyState(
                Modifier,
                R.drawable.empty_state_resource,
                stringResource(id = R.string.empty_state_title_resource),
                stringResource(id = R.string.empty_state_description_resource)
            )
        } else {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(dimensionResource(id = R.dimen.dimension_16dp)),
                query = viewModel.searchText,
                onQueryChange = { viewModel.updateSearchText(it) },
                onSearch = {
                    active = false
                    viewModel.getSearchingResult(it)
                },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = stringResource(id = R.string.search_bar_default_text)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (active) {
                        Icon(
                            modifier =
                            Modifier.clickable {
                                if (viewModel.searchText.isEmpty()) {
                                    active = false
                                }
                                viewModel.updateSearchText("")
                                viewModel.getSearchingResult("")
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                },
            ) {
            }
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(top = 76.dp),
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
