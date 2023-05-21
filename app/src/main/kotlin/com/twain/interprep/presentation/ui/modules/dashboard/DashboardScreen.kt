package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.AppBar
import com.twain.interprep.presentation.ui.components.ComingNextInterviewCard
import com.twain.interprep.presentation.ui.components.FAB
import com.twain.interprep.presentation.ui.components.Header
import com.twain.interprep.presentation.ui.components.PastInterviewCard
import com.twain.interprep.presentation.ui.components.UpcomingInterviewCard

@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = { AppBar(stringResource(id = R.string.nav_item_dashboard)) {} },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FAB {
                navController.navigate(AppScreens.AddInterview.route) {
                    popUpTo(AppScreens.Dashboard.route)
                }
            }
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.dimension_8dp))
            ) {
                item {
                    Column {
                        Header(
                            text = stringResource(id = R.string.heading_label_upcoming),
                            textStyle = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(
                                start = dimensionResource(id = R.dimen.dimension_8dp),
                                end = dimensionResource(id = R.dimen.dimension_8dp),
                                top = dimensionResource(id = R.dimen.dimension_8dp)
                            ),
                            fontWeight = FontWeight.Normal
                        )
                        LazyRow(
                            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dimension_8dp)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            items(5) { UpcomingInterviewCard() }
                        }
                    }
                }
                item {
                    Column {
                        Header(
                            text = stringResource(id = R.string.heading_label_coming_next),
                            textStyle = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(
                                start = dimensionResource(id = R.dimen.dimension_8dp),
                                end = dimensionResource(id = R.dimen.dimension_8dp),
                                top = dimensionResource(id = R.dimen.dimension_8dp)
                            ),
                            fontWeight = FontWeight.Normal
                        )
                        LazyRow(
                            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dimension_8dp))
                        ) {
                            items(5) { ComingNextInterviewCard() }
                        }
                    }
                }
                item {
                    Header(
                        text = stringResource(id = R.string.heading_label_past),
                        textStyle = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(
                            start = dimensionResource(id = R.dimen.dimension_8dp),
                            top = dimensionResource(id = R.dimen.dimension_8dp),
                            end = dimensionResource(id = R.dimen.dimension_8dp),
                        ),
                        fontWeight = FontWeight.Normal
                    )
                }
                items(25) { PastInterviewCard() }
            }
        }
    )
}
