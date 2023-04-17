package com.twain.interprep.presentation.ui.modules.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.components.AppBar
import com.twain.interprep.presentation.ui.components.FullScreenEmptyState
import com.twain.interprep.presentation.ui.components.FullScreenEmptyStateInterview
import com.twain.interprep.presentation.ui.components.FullScreenEmptyStateNotes
import com.twain.interprep.presentation.ui.modules.dashboard.DashboardViewModel

@Composable
fun NotesScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppBar(stringResource(id = R.string.nav_item_notes)){}
        FullScreenEmptyStateNotes()
    }
}
