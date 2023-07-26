package com.twain.interprep.presentation.ui.modules.resources

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.twain.interprep.R
import com.twain.interprep.data.ui.ResourceFormData
import com.twain.interprep.presentation.ui.components.generic.IPAlertDialog
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.components.generic.IPOutlinedButton
import com.twain.interprep.presentation.ui.components.generic.IPTextInput
import com.twain.interprep.presentation.ui.components.resource.AddLinkCard
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple

@Composable
fun AddResourceScreen(
    navController: NavController,
    resourceId: Int,
    viewModel: ResourcesViewModel = hiltViewModel()
) {
    val shouldShowAlert = remember { mutableStateOf(false) }
    // Flag to check if we should highlight any empty mandatory input field by showing an error message
    var shouldValidateFormFields by remember { mutableStateOf(false) }
    val isEdit = resourceId != 0

    LaunchedEffect(Unit) {
        viewModel.getResourceAndLinksByResourceId(resourceId)
    }
    BackHandler {
        handleBackPress(viewModel, navController, shouldShowAlert)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            IPAppBar(
                title = stringResource(
                    id = R.string.appbar_title_add_resource.takeUnless { isEdit }
                        ?: R.string.appbar_title_edit_resource),
                navIcon = {
                    IPIcon(imageVector = Icons.Filled.ArrowBack, tint = Color.White) {
                        handleBackPress(viewModel, navController, shouldShowAlert)
                    }
                },
                actions = {
                    if (isEdit) {
                        IPIcon(imageVector = Icons.Filled.Delete, tint = Color.White) {
                            viewModel.deleteResource()
                            navController.popBackStack()
                        }
                    }
                }
            )
        },
        content = { padding ->
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
                        shouldValidateFormFields = true
                    })
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(paddingValues = padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IPHeader(
                    stringResource(id = R.string.add_resource_header.takeUnless { isEdit }
                        ?: R.string.edit_resource_header),
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    MaterialTheme.typography.titleMedium,
                    Modifier
                        .padding(dimensionResource(id = R.dimen.dimension_16dp))
                        .align(Alignment.Start),
                    fontWeight = FontWeight.Normal
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
                        shouldValidate = shouldValidateFormFields
                    )
                }
                IPHeader(
                    stringResource(id = R.string.reference_link),
                    MaterialTheme.colorScheme.onSurface,
                    MaterialTheme.typography.titleMedium,
                    Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.dimension_16dp),
                            top = dimensionResource(id = R.dimen.dimension_16dp),
                            bottom = dimensionResource(id = R.dimen.dimension_4dp)
                        )
                        .align(Alignment.Start),
                    fontWeight = FontWeight.Medium
                )
                Column(
                    modifier = Modifier
                        .padding(bottom = dimensionResource(id = R.dimen.dimension_16dp))
                        .verticalScroll(rememberScrollState()),
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
                            isEdit = isEdit,
                            numberOfLinks = viewModel.links.size
                        )
                    }
                    if (!isEdit) {
                        Row(
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.dimension_16dp))
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IPOutlinedButton(
                                backgroundColor = BackgroundLightPurple,
                                text = stringResource(id = R.string.add_link),
                                textColor = Color.Black,
                                textStyle = MaterialTheme.typography.titleMedium,
                                enabled = viewModel.addLinkEnabled(),
                                iconColor = BackgroundDarkPurple,
                                borderColor = BackgroundDarkPurple,
                                leadingIcon = R.drawable.link_logo,
                                onClick = { viewModel.addLink() })
                        }
                    }
                }
            }
        }
    )
}

private fun handleBackPress(
    viewModel: ResourcesViewModel,
    navController: NavController,
    shouldShowAlert: MutableState<Boolean>
) {
    if (viewModel.isResourceValid() && viewModel.areAllLinksValid()) {
        viewModel.addAllLinks()
        navController.popBackStack()
    } else shouldShowAlert.value = true
}
