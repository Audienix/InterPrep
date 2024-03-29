package com.twain.interprep.presentation.ui.modules.resources

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.data.ui.ResourceFormData
import com.twain.interprep.presentation.ui.components.generic.IPAlertDialog
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPFilledButton
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.components.generic.IPTextInput
import com.twain.interprep.presentation.ui.components.resource.AddLinkCard
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun AddResourceScreen(
    navController: NavHostController,
    resourceId: Int,
    viewModel: ResourcesViewModel = hiltViewModel()
) {
    val shouldShowAlert = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }
    // Flag to check if we should highlight any empty mandatory input field by showing an error message
    val isEdit = resourceId != 0

    LaunchedEffect(Unit) {
        viewModel.getResourceAndLinksByResourceId(resourceId)
    }
    BackHandler {
        handleBackPress(viewModel, navController, shouldShowAlert)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            IPAppBar(
                title = stringResource(
                    id = R.string.appbar_title_add_resource.takeUnless { isEdit }
                        ?: R.string.appbar_title_edit_resource),
                navIcon = {
                    IPIcon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = MaterialColorPalette.onSurfaceVariant
                    ) {
                        handleBackPress(viewModel, navController, shouldShowAlert)
                    }
                },
                actions = {
                    if (isEdit) {
                        IPIcon(
                            imageVector = Icons.Filled.Delete,
                            tint = MaterialColorPalette.onSurfaceVariant
                        ) {
                            showDeleteDialog.value = true
                        }
                    }
                }
            )
        },
        content = { padding ->
            ShowDeleteConfirmationDialog(
                showDeleteDialog = showDeleteDialog,
                navController = navController
            )
            ShowScreenContent(
                padding = padding,
                isEdit = isEdit,
                shouldShowAlert = shouldShowAlert,
                navController = navController
            )
        },
        containerColor = MaterialColorPalette.surface,
    )
}

private fun handleBackPress(
    viewModel: ResourcesViewModel,
    navController: NavController,
    shouldShowAlert: MutableState<Boolean>
) {
    if (viewModel.isResourceAndLinksValid()) {
        viewModel.addAllLinks()
        navController.popBackStack()
    } else shouldShowAlert.value = true
}

@Composable
private fun showBackConfirmationDialog(
    shouldShowAlert: MutableState<Boolean>,
    navController: NavHostController,
    shouldValidateFormFields: Boolean
): Boolean {
    var shouldValidateFormFields1 = shouldValidateFormFields
    if (shouldShowAlert.value) {
        IPAlertDialog(
            titleResId = R.string.alert_dialog_unsaved_resource_title,
            contentResId = R.string.alert_dialog_unsaved_resource_text,
            // "OK" is clicked
            onPositiveButtonClick = {
                shouldShowAlert.value = false
                navController.popBackStack()
            },
            // "CANCEL" is clicked
            onNegativeButtonClick = {
                shouldShowAlert.value = false
                shouldValidateFormFields1 = true
            })
    }
    return shouldValidateFormFields1
}

@Composable
private fun ShowScreenContent(
    padding: PaddingValues,
    isEdit: Boolean,
    shouldShowAlert: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: ResourcesViewModel = hiltViewModel()
) {
    var shouldValidateFormFields by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(paddingValues = padding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IPHeader(
            text = stringResource(id = R.string.header_add_resource.takeUnless { isEdit }
                ?: R.string.header_edit_resource),
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.dimension_16dp))
                .align(Alignment.Start)
        )
        ResourceFormData.resourceForm.map { input ->
            IPTextInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp)),
                inputText = viewModel.getResourceField(input.labelTextId),
                textInputAttributes = input,
                onTextUpdate = {
                    viewModel.updateResourceField(input.labelTextId, it)
                },
                isBackPressed = shouldValidateFormFields
            )
        }
        IPHeader(
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.dimension_16dp),
                )
                .align(Alignment.Start),
            text = stringResource(id = R.string.header_reference_link),
            textStyle = MaterialTheme.typography.bodyMedium,
        )
        Column(
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.dimension_16dp)),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            viewModel.links.forEachIndexed { index, link ->
                AddLinkCard(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.dimension_16dp),
                            end = dimensionResource(id = R.dimen.dimension_16dp),
                            top = dimensionResource(id = R.dimen.dimension_16dp),
                            bottom = dimensionResource(id = R.dimen.dimension_4dp)
                        )
                        .fillMaxWidth(),
                    getLinkField = { viewModel.getLinkField(resId = it, index = index) },
                    updateLinkField = { resId, value ->
                        viewModel.updateLinkField(
                            resId,
                            index,
                            value
                        )
                    },
                    deleteLink = { viewModel.deleteLink(link) },
                    shouldValidate = shouldValidateFormFields,
                    numberOfCurrentLinks = viewModel.links.size
                )
            }
            if (!isEdit) {
                Row(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.dimension_16dp))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IPFilledButton(
                        backgroundColor = MaterialColorPalette.primaryContainer,
                        text = stringResource(id = R.string.button_add_link),
                        textColor = MaterialColorPalette.onPrimaryContainer,
                        textStyle = MaterialTheme.typography.labelLarge,
                        enabled = viewModel.addLinkEnabled(),
                        iconColor = MaterialColorPalette.onPrimaryContainer,
                        leadingIcon = R.drawable.ic_add_link,
                        disabledContentColor = MaterialColorPalette.onSurfaceVariant,
                        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dimension_16dp)),
                        onClick = { viewModel.addLink() })
                }
            }
        }
    }

    shouldValidateFormFields =
        showBackConfirmationDialog(shouldShowAlert, navController, shouldValidateFormFields)
}

@Composable
private fun ShowDeleteConfirmationDialog(
    showDeleteDialog: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: ResourcesViewModel = hiltViewModel()
) {
    if (showDeleteDialog.value) {
        IPAlertDialog(
            titleResId = R.string.alert_dialog_delete_resource_title,
            contentResId = R.string.alert_dialog_delete_resource_text,
            // "OK" is clicked
            onPositiveButtonClick = {
                viewModel.deleteResource()
                showDeleteDialog.value = false
                navController.popBackStack()
            },
            // "CANCEL" is clicked
            onNegativeButtonClick = {
                showDeleteDialog.value = false
            },
        )
    }
}
