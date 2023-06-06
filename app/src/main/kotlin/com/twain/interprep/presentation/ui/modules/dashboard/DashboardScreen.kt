package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.data.ui.QuoteData
import com.twain.interprep.data.ui.interviewMockData
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPFAB
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.components.interview.PastInterviewCard
import com.twain.interprep.presentation.ui.modules.interview.QuotesViewModel

@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: InterviewViewModel = hiltViewModel(),
    quotesViewModel: QuotesViewModel = hiltViewModel()
) {
    // TODO ask Arighna
    quotesViewModel.insertQuotes(QuoteData.quotes)
    LaunchedEffect(Unit) {
        viewModel.getInterviews()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = { IPAppBar(stringResource(id = R.string.nav_item_dashboard)) {} },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            IPFAB {
                navController.navigate(AppScreens.AddInterview.route) {
                    popUpTo(AppScreens.Dashboard.route)
                }
            }
        },
        content = { padding ->
            if (viewModel.interviews is ViewResult.Loaded) {
                val interviews = viewModel.interviews as ViewResult.Loaded
                if (interviews.data.isEmptyInterviewList) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        FullScreenEmptyState(
                            Modifier,
                            R.drawable.empty_state_dashboard,
                            stringResource(id = R.string.empty_state_title_dashboard),
                            stringResource(id = R.string.empty_state_description_dashboard)
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.padding(padding),
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.dimension_8dp))
                ) {
                    if (interviews.data.upcomingInterviews.isNotEmpty()) {
                        item {
                            Column {
                                IPHeader(
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
                                    items(interviews.data.upcomingInterviews) { interview ->
                                        InterviewCard(
                                            interview = interview,
                                            onClick = { /*TODO*/ },
                                            color = InterviewCardColor.UpcomingInterviewCardColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (interviews.data.comingNextInterviews.isNotEmpty()) {
                        item {
                            Column {
                                IPHeader(
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
                                    items(interviews.data.comingNextInterviews) { interview ->
                                        InterviewCard(
                                            interview = interview,
                                            onClick = { /*TODO*/ },
                                            color = InterviewCardColor.ComingNextInterviewColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (interviews.data.pastInterviews.isNotEmpty()) {
                        item {
                            IPHeader(
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
                        items(interviews.data.pastInterviews) { interview ->
                            InterviewCard(
                                interview = interview,
                                onClick = { /*TODO*/ },
                                color = InterviewCardColor.PastInterviewCardColor
                            )
                        }
                    }
                }
            }
        }
    )
}
