package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.AppBar
import com.twain.interprep.presentation.ui.components.Header
import com.twain.interprep.presentation.ui.components.TextFormInput
import com.twain.interprep.utils.AddInterviewData.Companion.inputHorizontals
import com.twain.interprep.utils.AddInterviewData.Companion.inputVerticals

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInterviewScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            AppBar(stringResource(id = R.string.appbar_title_add_interview)) {
                IconButton(onClick = {
                    navController.navigate(AppScreens.Dashboard.route) {
                        popUpTo(AppScreens.AddInterview.route)
                    }
                }) {
                    Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                }
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp))
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimension_16dp))
            ) {
                Header(
                    stringResource(id = R.string.add_interview_header),
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    MaterialTheme.typography.titleSmall,
                    Modifier.padding(top = dimensionResource(id = R.dimen.dimension_16dp)),
                    fontWeight = FontWeight.Normal
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.dimension_8dp)
                    )
                ) {
                    inputHorizontals.map {
                        TextFormInput(modifier = Modifier.weight(1f), it)
                    }
                }
                inputVerticals.map {
                    TextFormInput(input = it)
                }

            }
        }
    )
}
