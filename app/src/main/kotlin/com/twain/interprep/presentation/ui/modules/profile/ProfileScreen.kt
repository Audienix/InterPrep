package com.twain.interprep.presentation.ui.modules.profile

import androidx.compose.foundation.background
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
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.components.generic.IPAvatar
import com.twain.interprep.presentation.ui.components.generic.IPCircleTextIcon
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.setProfileSettings()
    }

    Scaffold(
        topBar = { ProfileTopBar(viewModel.getDisplayedName()) {
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
fun ProfileColumn(
    modifier: Modifier,
    items: List<ProfileRowData>,
    onItemClick: (ClickAction) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = items) {
            ProfileColumnItem(data = it, onClick = onItemClick)
        }

    }
}


@Composable
fun ProfileColumnItem(
    data: ProfileRowData,
    onClick: (ClickAction) -> Unit
) {
    Row {
        IPAvatar(
            size = dimensionResource(id = R.dimen.dimension_icon_size_40),
            containerColor = MaterialColorPalette.secondaryContainer,
            image = data.imageVector
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = data.title
            )
            Text(
                text = data.label
            )
        }
        IPIcon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            tint = MaterialColorPalette.onSurfaceVariant) { onClick(data.clickAction) }
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
            size = dimensionResource(id = R.dimen.dimension_icon_size_48),
            text = name,
            containerColor = MaterialColorPalette.secondary,
            textStyle = MaterialTheme.typography.displaySmall
        )

    }
}
