package com.twain.interprep.presentation.ui.modules.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.BuildConfig
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.components.generic.IPAvatar
import com.twain.interprep.presentation.ui.components.generic.IPCircleTextIcon
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
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
        topBar = { ProfileTopBar(getNameInitials(input = viewModel.profileSettings.userName)) {
            navController.popBackStack()
        } }
    ) { paddingValues ->

        ProfileColumn(
            modifier = Modifier.padding(paddingValues),
            items = viewModel.getProfileRowData(),
            onItemClick = viewModel::handleAction
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
    items: List<ProfileRowData>,
    onItemClick: (ClickAction) -> Unit
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
    data: ProfileRowData,
    onClick: (ClickAction) -> Unit
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
                    text = data.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialColorPalette.onSurface
                )
                Text(
                    text = data.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialColorPalette.onSurfaceVariant
                )
            }
            IPIcon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                tint = MaterialColorPalette.onSurfaceVariant) { onClick(data.clickAction) }
        }
        HorizontalDivider()
    }
}

@Composable
fun AppVersion() {
    Row {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "App Version: ${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialColorPalette.onSurfaceVariant
        )
    }
}
