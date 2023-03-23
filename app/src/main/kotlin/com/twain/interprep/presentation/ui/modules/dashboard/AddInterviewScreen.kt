package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.AppBar

@Composable
fun AddInterviewScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AppBar(stringResource(id = R.string.appbar_title_add_interview)) {
            IconButton(onClick = {
                navController.navigate(AppScreens.Dashboard.route) {
                    popUpTo(AppScreens.AddInterview.route)
                }
            }) {
                Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
            }
        }
    }
}