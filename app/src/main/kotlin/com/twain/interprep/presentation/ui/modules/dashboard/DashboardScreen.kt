package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.AppBar
import com.twain.interprep.presentation.ui.components.FAB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppBar(stringResource(id = R.string.nav_item_dashboard)){}
        Scaffold(
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FAB {
                    navController.navigate(AppScreens.AddInterview.route) {
                        popUpTo(AppScreens.Dashboard.route)
                    }
                }
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {}
            }
        )
    }
}