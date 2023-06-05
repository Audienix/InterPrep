package com.twain.interprep.presentation.ui.modules.interview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.twain.interprep.R
import com.twain.interprep.data.ui.QuoteData
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPQuoteCard
import com.twain.interprep.presentation.ui.components.interview.IPInterviewDetailsCard
import com.twain.interprep.presentation.ui.components.interview.interview
import com.twain.interprep.presentation.ui.modules.dashboard.DashboardViewModel

@Composable
fun InterviewDetailsScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            IPAppBar(stringResource(id = R.string.appbar_title_interview_details)) {
                IconButton(onClick = {
                    navController.popBackStack()
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
                    .padding(dimensionResource(id = R.dimen.dimension_4dp))
                    .verticalScroll(rememberScrollState()),
            ) {
                IPQuoteCard(quote = QuoteData.quotes[0])
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
                IPInterviewDetailsCard(interview = interview)
            }
        }
    )
}

@Preview
@Composable
fun InterviewDetailsScreenPreview(){
    InterviewDetailsScreen(navController = rememberNavController() )
}