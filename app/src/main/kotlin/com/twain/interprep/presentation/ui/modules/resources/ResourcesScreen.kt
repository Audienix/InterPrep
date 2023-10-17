package com.twain.interprep.presentation.ui.modules.resources

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { IPAppBar(stringResource(id = R.string.nav_item_resources)) },
        containerColor = MaterialColorPalette.surface,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            IPFAB {
                navController.navigate(AppScreens.MainScreens.AddResource.withArgs(0)) {
                    popUpTo(AppScreens.MainScreens.Resources.route)
                }
            }
        },
        content = { padding ->
            ShowResourcesScreenContent(navController, viewModel, padding)
        }
    )
}

@Composable
private fun ShowResourcesScreenContent(
    navController: NavHostController,
    viewModel: ResourcesViewModel = hiltViewModel(),
    padding: PaddingValues,
) {
    LaunchedEffect(Unit) {
        // Fetch the full resource list when the screen is first created
        viewModel.getAllResources()
    }
    Column(modifier = Modifier.padding(padding)) {
        if (viewModel.allResourcesWithLinks is ViewResult.Loaded) {
            val resourceAndLinks =
                (viewModel.allResourcesWithLinks as
                        ViewResult.Loaded<List<Pair<Resource, List<ResourceLink>>>>).data
            if (resourceAndLinks.isEmpty()) {
                // Display empty state when no resources are available
                FullScreenEmptyState(
                    Modifier,
                    R.drawable.ic_empty_resources,
                    stringResource(id = R.string.empty_state_title_resource),
                    stringResource(id = R.string.empty_state_description_resource)
                )
            } else {
                ShowSearchBar()
                ShowResourceList(resourceAndLinks, navController)
            }
        }
    }
}

@Composable
private fun ShowResourceList(
    resourceAndLinks: List<Pair<Resource, List<ResourceLink>>>,
    navController: NavHostController
) {
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.dimension_16dp)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_16dp))
    ) {
        items(resourceAndLinks) { (resource, links) ->
            ResourceCard(
                resourceAndLinks = resource to links,
                showEditIcon = true,
                onEditResourceClick = {
                    navController.navigate(
                        AppScreens.MainScreens.AddResource.withArgs(resource.resourceId)
                    )
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ShowSearchBar(
    viewModel: ResourcesViewModel = hiltViewModel()
) {
    var isSearchBarActive by remember { mutableStateOf(false) }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dimension_16dp)),
        query = viewModel.searchText,
        onQueryChange = {
            viewModel.updateSearchText(it)
        },
        onSearch = {
            viewModel.getSearchResultResources(it)
        },
        active = isSearchBarActive,
        onActiveChange = { isSearchBarActive = it },
        placeholder = { Text(text = stringResource(id = R.string.hint_label_search_resource)) },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialColorPalette.surfaceContainerHigh,
            inputFieldColors = SearchBarDefaults.inputFieldColors(
                focusedTextColor = MaterialColorPalette.onSurface,
                unfocusedTextColor = MaterialColorPalette.onSurface,
                focusedLeadingIconColor = MaterialColorPalette.onSurface,
                unfocusedLeadingIconColor = MaterialColorPalette.onSurface,
                focusedTrailingIconColor = MaterialColorPalette.onSurfaceVariant,
                unfocusedTrailingIconColor = MaterialColorPalette.onSurfaceVariant,
                focusedPlaceholderColor = MaterialColorPalette.onSurfaceVariant,
                unfocusedPlaceholderColor = MaterialColorPalette.onSurfaceVariant
            ),
            dividerColor = MaterialColorPalette.outlineVariant
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialColorPalette.onSurface
            )
        },
        trailingIcon = {
            /** If the dismiss button is clicked with the empty searchText, the expanding sheet
             * of the searchBar will disappear. Then searchText is removed from the
             * SearchBar and ResourcesScreen will display the list of all resource and
             * links pairs.
             * **/
            if (isSearchBarActive) {
                Icon(
                    modifier =
                    Modifier.clickable {
                        if (viewModel.searchText.isEmpty()) {
                            isSearchBarActive = false
                        }
                        viewModel.updateSearchText("")
                        viewModel.getSearchResultResources("")
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialColorPalette.onSurfaceVariant
                )
            }
        },
    ) {
        if (isSearchBarActive && viewModel.filteredResources is ViewResult.Loaded) {
            val filteredResults =
                (viewModel.filteredResources as
                        ViewResult.Loaded<List<Pair<Resource, List<ResourceLink>>>>).data
            if (filteredResults.isEmpty()) {
                FullScreenEmptyState(
                    Modifier,
                    R.drawable.ic_empty_resources,
                    stringResource(id = R.string.empty_state_title_no_match_resource),
                    stringResource(id = R.string.empty_state_description_no_match_resource)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.dimension_8dp)),
                    verticalArrangement =
                    Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
                ) {
                    items(filteredResults) { (resource, links) ->
                        ResourceCard(
                            resourceAndLinks = resource to links,
                            showEditIcon = false,
                            onEditResourceClick = {
                            }
                        )
                    }
                }
            }
        }
    }
}
