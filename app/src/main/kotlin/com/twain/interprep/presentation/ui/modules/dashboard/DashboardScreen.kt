package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.data.model.DashboardInterviewType
import com.twain.interprep.data.model.DashboardInterviews
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.FullScreenEmptyState
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPFAB
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.components.interview.InterviewBottomSheet
import com.twain.interprep.presentation.ui.components.interview.InterviewCard
import com.twain.interprep.presentation.ui.modules.interview.InterviewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    interviewModel: InterviewViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        dashboardViewModel.getInterviews()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = { IPAppBar(stringResource(id = R.string.nav_item_dashboard)) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            IPFAB {
                interviewModel.interviewData = Interview()
                navController.navigate(AppScreens.AddInterview.withArgs(0)) {
                    popUpTo(AppScreens.Dashboard.route)
                }
            }
        },
        content = { padding ->
            ShowDashboardScreenContent(dashboardViewModel, padding, interviewModel, navController)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDashboardScreenContent(
    dashboardViewModel: DashboardViewModel,
    padding: PaddingValues,
    interviewModel: InterviewViewModel,
    navController: NavHostController
) {
    val openBottomSheet = rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    if (dashboardViewModel.interviews is ViewResult.Loaded) {
        val interviews = dashboardViewModel.interviews as ViewResult.Loaded
        if (interviews.data.isEmptyInterviewList)
            ShowEmptyState()

        if (dashboardViewModel.interviews is ViewResult.Loaded) {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.dimension_8dp))
            ) {
                if (interviews.data.upcomingInterviews.isNotEmpty()) {
                    item {
                        ShowUpcomingInterviews(interviews, interviewModel, navController)
                    }
                }
                if (interviews.data.comingNextInterviews.isNotEmpty()) {
                    item {
                        ShowComingNextInterviews(interviews, interviewModel, navController)
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
                            onClick = { interviewModel.interviewData = interview },
                            navController = navController,
                            dashboardInterviewType = DashboardInterviewType.PastInterview(),
                            onStatusBarClicked = {
                                interviewModel.interviewData = interview
                                openBottomSheet.value = true
                            }
                        )
                    }
                }
            }
        }
    } else {
        ShowLoadingState()
    }
    ShowInterviewStatusBottomSheet(openBottomSheet, bottomSheetState, scope, interviewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowInterviewStatusBottomSheet(
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    scope: CoroutineScope,
    interviewModel: InterviewViewModel
) {
    if (openBottomSheet.value) {
        InterviewBottomSheet(
            onDismissRequest = { openBottomSheet.value = false },
            bottomSheetState = bottomSheetState,
            onNewStatusSelected = {
                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        openBottomSheet.value = false
                    }
                }
                interviewModel.updateInterviewStatus(it)
            },
            highlightedStatus = interviewModel.interviewData.interviewStatus
        )
    }
}

@Composable
private fun ShowComingNextInterviews(
    interviews: ViewResult.Loaded<DashboardInterviews>,
    interviewModel: InterviewViewModel,
    navController: NavHostController
) {
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
            modifier = Modifier.padding(
                bottom = dimensionResource(
                    id = R.dimen.dimension_8dp
                )
            )
        ) {
            items(interviews.data.comingNextInterviews) { interview ->
                InterviewCard(
                    interview = interview,
                    onClick = {
                        interviewModel.interviewData = interview
                    },
                    navController = navController,
                    dashboardInterviewType = DashboardInterviewType.NextInterview()
                )
            }
        }
    }
}

@Composable
private fun ShowUpcomingInterviews(
    interviews: ViewResult.Loaded<DashboardInterviews>,
    interviewModel: InterviewViewModel,
    navController: NavHostController
) {
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
            modifier = Modifier.padding(
                bottom = dimensionResource(
                    id = R.dimen.dimension_8dp
                )
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(interviews.data.upcomingInterviews) { interview ->
                InterviewCard(
                    interview = interview,
                    onClick = {
                        interviewModel.interviewData = interview
                    },
                    navController = navController,
                    dashboardInterviewType = DashboardInterviewType.UpcomingInterview()
                )
            }
        }
    }
}

@Composable
private fun ShowEmptyState() {
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

@Composable
private fun ShowLoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
