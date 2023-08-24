package com.twain.interprep.presentation.ui.modules.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.BuildConfig
import com.twain.interprep.R
import com.twain.interprep.data.ui.ProfileSettingsData.ClickAction
import com.twain.interprep.data.ui.ProfileSettingsData.ProfileSettingsItemData
import com.twain.interprep.data.ui.TextInputAttributes
import com.twain.interprep.presentation.ui.components.generic.IPAvatar
import com.twain.interprep.presentation.ui.components.generic.IPCircleTextIcon
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.components.generic.IPTextInput
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes
import com.twain.interprep.utils.getNameInitials

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.setProfileSettings()
    }

    Scaffold(
        topBar = {
            ProfileTopBar(getNameInitials(input = viewModel.preferenceItem.userName)) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        val context = LocalContext.current

        viewModel.action?.let {
            HandleAction(action = it, viewModel = viewModel)
        }

        ProfileColumn(
            modifier = Modifier.padding(paddingValues),
            items = viewModel.getProfileSettingsItemDataList(context),
            onItemClick = viewModel::setAction
        )
    }
}

@Composable
fun ProfileTopBar(
    name: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dimension_188dp))
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dimension_140dp))
                .background(MaterialColorPalette.secondaryContainer)
        )
        IPIcon(
            imageVector = Icons.Filled.ArrowBack,
            tint = MaterialColorPalette.onSurfaceVariant,
            onIconClick = onBackClick
        )
        IPCircleTextIcon(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            size = dimensionResource(id = R.dimen.dimension_icon_size_96),
            text = name,
            containerColor = MaterialColorPalette.secondary,
            textStyle = MaterialTheme.typography.displaySmall,
            textColor = MaterialColorPalette.onSecondary
        )
    }
}

@Composable
fun ProfileColumn(
    modifier: Modifier,
    items: List<ProfileSettingsItemData>,
    onItemClick: (ClickAction, String?) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp))
            .padding(top = dimensionResource(id = R.dimen.dimension_8dp)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_16dp))
    ) {
        items(items = items) {
            ProfileColumnItem(data = it, onClick = onItemClick)
        }
        item {
            AppVersion()
        }
    }
}


@Composable
fun ProfileColumnItem(
    data: ProfileSettingsItemData,
    onClick: (ClickAction, String?) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.dimension_8dp)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_16dp))
        ) {
            IPAvatar(
                size = dimensionResource(id = R.dimen.dimension_icon_size_40),
                containerColor = MaterialColorPalette.secondaryContainer,
                image = data.imageVector,
                imageRes = data.imageRes
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(id = data.title),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialColorPalette.onSurface
                )
                if (data.label != null) {
                    Text(
                        text = data.label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialColorPalette.onSurfaceVariant
                    )
                }
            }
            IPIcon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                tint = MaterialColorPalette.onSurfaceVariant
            ) { onClick(data.clickAction, data.label) }
        }
        HorizontalDivider()
    }
}

@Composable
fun AppVersion() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.align(Alignment.CenterEnd),
            text = "${stringResource(id = R.string.label_setting_app_version)}${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialColorPalette.onSurfaceVariant
        )
    }
}

@Composable
fun HandleAction(action: ClickAction, viewModel: ProfileViewModel) {
    when (action) {
        ClickAction.NAME -> HandleNameClick(viewModel)
        ClickAction.PREFERRED_LANGUAGE -> TODO()
        ClickAction.APP_THEME -> TODO()
        ClickAction.NOTIFICATION_REMINDER -> TODO()
        ClickAction.RATING_FEEDBACK -> TODO()
        ClickAction.PRIVACY_POLICY -> TODO()
    }
}

@Composable
fun HandleNameClick(viewModel: ProfileViewModel) {
    IPTextInputDialog(
        titleRes = R.string.name,
        inputText = viewModel.currentPopupValue,
        onTextUpdate = { viewModel.currentPopupValue = it  },
        textInputAttributes = TextInputAttributes(
            labelTextId = R.string.name
        ),
        onCancelClick =  { viewModel.setAction(null, "")},
        onConfirmClick = { viewModel.setName(viewModel.currentPopupValue) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IPTextInputDialog(
    @StringRes titleRes: Int,
    inputText: String,
    onTextUpdate: (String) -> Unit,
    textInputAttributes: TextInputAttributes,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {}) {
        Surface(
            shape = Shapes.extraLarge,
            color = MaterialColorPalette.surfaceContainerHigh,
            tonalElevation = dimensionResource(id = R.dimen.dimension_6dp),
        ) {
            Column(
                modifier = Modifier.padding(
                    PaddingValues(all = dimensionResource(
                    id = R.dimen.dimension_24dp))
                )
            ) {
                Text(
                    text = stringResource(id = titleRes),
                    color = MaterialColorPalette.onSurface,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_16dp)))
                IPTextInput(
                    inputText = inputText,
                    textInputAttributes = textInputAttributes,
                    onTextUpdate = onTextUpdate
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_20dp)))
                Row(
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onCancelClick) {
                        Text(text = "Cancel")
                    }
                    TextButton(onClick = onConfirmClick) {
                        Text(text = "Ok")
                    }
                }
            }
        }
    }
}
