package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import com.twain.interprep.presentation.ui.theme.DashBoardBackgroundGray
import com.twain.interprep.presentation.ui.theme.DashBoardBackgroundGreen
import com.twain.interprep.presentation.ui.theme.DashBoardBackgroundPurple
import com.twain.interprep.presentation.ui.theme.DashBoardHeader
import com.twain.interprep.presentation.ui.theme.Shapes

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
                Box(modifier = Modifier.padding(padding)) {
                    Column{
                        Card(modifier = Modifier.padding(padding)) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = Shapes.small,
                                colors = CardDefaults.cardColors(containerColor = DashBoardBackgroundPurple)
                            ) {
                                Header(
                                    text = "Upcoming",
                                    textColor = DashBoardHeader,
                                    textStyle = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(16.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                LazyRow(
                                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    items(5) { UpcomingInterviewCard() }
                                }
                            }
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                shape = Shapes.small,
                                colors = CardDefaults.cardColors(containerColor = DashBoardBackgroundGreen)
                            ) {
                                Header(
                                    text = "Coming Next",
                                    textColor = DashBoardHeader,
                                    textStyle = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(16.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                LazyRow(
                                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(5) { ComingNextInterviewCard() }
                                }
                            }
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                shape = Shapes.small,
                                colors = CardDefaults.cardColors(containerColor = DashBoardBackgroundGray)
                            ) {
                                Header(
                                    text = "Past",
                                    textColor = DashBoardHeader,
                                    textStyle = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(16.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                LazyColumn(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(5) { PastInterviewCard() }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}