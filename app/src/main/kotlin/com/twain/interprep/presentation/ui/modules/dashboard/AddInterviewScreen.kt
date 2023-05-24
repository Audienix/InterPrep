package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.twain.interprep.presentation.ui.components.IPAppBar
import com.twain.interprep.presentation.ui.components.IPHeader
import com.twain.interprep.presentation.ui.components.IPTextInput
import com.twain.interprep.data.ui.AddInterviewData.Companion.textInputHorizontalList
import com.twain.interprep.data.ui.AddInterviewData.Companion.textInputVerticalList

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
            IPAppBar(stringResource(id = R.string.appbar_title_add_interview)) {
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
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                IPHeader(
                    stringResource(id = R.string.add_interview_header),
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    MaterialTheme.typography.titleMedium,
                    Modifier.padding(vertical = dimensionResource(id = R.dimen.dimension_16dp)),
                    fontWeight = FontWeight.Normal
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.dimension_8dp)
                    )
                ) {
                    textInputHorizontalList.map {
                        IPTextInput(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f), it
                        )
                    }
                }
                textInputVerticalList.map {
                    IPTextInput(
                        modifier = Modifier.fillMaxWidth(), input = it
                    )
                }

            }
        }
    )
}
