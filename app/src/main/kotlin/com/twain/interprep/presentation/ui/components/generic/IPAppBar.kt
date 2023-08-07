package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IPAppBar(
    title: String,
    navIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialColorPalette.surfaceContainerLow,
            titleContentColor = MaterialColorPalette.onSurface,
        ),
        navigationIcon = navIcon,
        actions = actions
    )
}
