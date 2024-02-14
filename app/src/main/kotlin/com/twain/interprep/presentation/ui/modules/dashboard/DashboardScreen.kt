package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewList
import com.twain.interprep.data.model.InterviewType
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.data.model.isEmpty
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.dashboard.DashboardInterviewCard
import com.twain.interprep.presentation.ui.components.generic.FullScreenEmptyState
import com.twain.interprep.presentation.ui.components.generic.IPBottomSheet
import com.twain.interprep.presentation.ui.components.generic.IPFAB
import com.twain.interprep.presentation.ui.components.generic.IPLargeAppBar
import com.twain.interprep.presentation.ui.components.generic.IPLoadingState
import com.twain.interprep.presentation.ui.modules.interview.InterviewViewModel
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes
import com.twain.interprep.utils.getFirstName
import com.twain.interprep.utils.getInterviewCardColorPair
import com.twain.interprep.utils.getInterviewEmptyStateTextPair
import com.twain.interprep.utils.getNameInitials
import com.twain.interprep.utils.getTimeOfDayGreeting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    interviewModel: InterviewViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        dashboardViewModel.getUsername()
        dashboardViewModel.getTodayInterviews()
        dashboardViewModel.getInterviews()
    }
    val interviewListState = rememberLazyListState()
    val directionalLazyListState = rememberDirectionalLazyListState(
        interviewListState
    )
    val selectedInterviewList = remember {
        mutableStateOf(emptyList<Interview>())
    }

    val isScrollingUp = remember { mutableStateOf(false) }
    when (directionalLazyListState.scrollDirection) {
        ScrollDirection.UP -> isScrollingUp.value = true
        ScrollDirection.DOWN -> isScrollingUp.value = false
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (dashboardViewModel.todayInterviewState is ViewResult.Loaded) {
                val todayInterviewList =
                    (dashboardViewModel.todayInterviewState as ViewResult.Loaded<List<Interview>>).data
                IPLargeAppBar(
                    title = "${stringResource(R.string.hello)} ${getFirstName(input = dashboardViewModel.username)}",
                    subtitle = "${stringResource(R.string.good)} ${getTimeOfDayGreeting()}",
                    todayInterviewList = todayInterviewList,
                    username = getNameInitials(dashboardViewModel.username),
                    isInterviewDetailsVisible = isScrollingUp,
                    navController = navController,
                    onAvatarClick = {
                        navController.navigate(AppScreens.MainScreens.Profile.route) {
                            popUpTo(AppScreens.MainScreens.Dashboard.route)
                        }
                    }
                )
            }
        },
        containerColor = MaterialColorPalette.surface,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            IPFAB {
                interviewModel.interviewData = Interview()
                navController.navigate(AppScreens.MainScreens.AddInterview.withArgs(0)) {
                    popUpTo(AppScreens.MainScreens.Dashboard.route)
                }
            }
        },
        content = { padding ->
            ShowDashboardScreenContent(
                dashboardViewModel,
                padding,
                interviewModel,
                navController,
                interviewListState,
                selectedInterviewList
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDashboardScreenContent(
    dashboardViewModel: DashboardViewModel,
    padding: PaddingValues,
    interviewModel: InterviewViewModel,
    navController: NavHostController,
    interviewListState: LazyListState,
    selectedInterviewList: MutableState<List<Interview>>
) {
    val openBottomSheet = rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    val selectedFilterIndex = remember { mutableIntStateOf(0) }

    if (dashboardViewModel.interviewListMetaData is ViewResult.Loaded) {
        val interviews = dashboardViewModel.interviewListMetaData as ViewResult.Loaded
        Column(
            modifier = Modifier.padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (interviews.data.isEmpty())
                ShowEmptyState(
                    title = stringResource(id = R.string.empty_state_title_dashboard),
                    description = stringResource(id = R.string.empty_state_description_dashboard)
                )
            else {
                InterviewTypeFilter(selectedFilterIndex)
                val interviewList: InterviewList = when (selectedFilterIndex.intValue) {
                    0 -> interviews.data.upcomingInterviewList
                    1 -> interviews.data.comingNextInterviewList
                    else -> interviews.data.pastInterviewList
                }
                selectedInterviewList.value = interviewList.list
                val interviewType: InterviewType = when (selectedFilterIndex.intValue) {
                    0 -> InterviewType.PRESENT
                    1 -> InterviewType.FUTURE
                    else -> InterviewType.PAST
                }
                if (interviewList.list.isNotEmpty()) {
                    LazyColumn(
                        contentPadding = PaddingValues(top = dimensionResource(id = R.dimen.dimension_8dp)),
                        state = interviewListState,
                    ) {

                        showInterviewList(
                            interviewType = interviewType,
                            interviewListState = interviewListState,
                            interviewList = interviewList,
                            dashboardViewModel = dashboardViewModel,
                            interviewModel = interviewModel,
                            navController = navController,
                            openBottomSheet
                        )
                    }
                } else {
                    val textPair = getInterviewEmptyStateTextPair(interviewType)
                    ShowEmptyState(
                        modifier = Modifier.padding(
                            bottom = dimensionResource(id = R.dimen.dimension_24dp)
                        ),
                        textPair.first, textPair.second
                    )
                }

            }
        }

    } else {
        IPLoadingState()
    }
    ShowInterviewStatusBottomSheet(openBottomSheet, bottomSheetState, scope, interviewModel)
}

@Composable
private fun ShowEmptyState(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        FullScreenEmptyState(
            Modifier,
            R.drawable.ic_empty_dashboard,
            title, description
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun InterviewTypeFilter(selectedFilterIndex: MutableIntState) {
    val filterItems = stringArrayResource(id = R.array.interview_filter)
    val selectedFilterItem = remember { mutableStateOf(filterItems[0]) }
    val interviewType: InterviewType = when (selectedFilterIndex.intValue) {
        0 -> InterviewType.PRESENT
        1 -> InterviewType.FUTURE
        else -> InterviewType.PAST
    }
    val interviewColorPair = getInterviewCardColorPair(type = interviewType)
    Box(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.dimension_8dp))) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        dimensionResource(id = R.dimen.dimension_stroke_width_low),
                        MaterialColorPalette.surfaceContainer
                    ),
                    shape = Shapes.extraLarge
                )
                .background(
                    color = MaterialColorPalette.surfaceContainerLowest,
                    shape = Shapes.extraLarge
                ),
            space = dimensionResource(id = R.dimen.dimension_4dp)
        ) {
            filterItems.forEach { item ->
                SegmentedButton(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.dimension_4dp)),
                    shape = Shapes.extraLarge,
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = interviewColorPair.first,
                        activeContentColor = interviewColorPair.second,
                        activeBorderColor = Color.Transparent,
                        inactiveContainerColor = Color.Transparent,
                        inactiveContentColor = MaterialColorPalette.onPrimaryContainer,
                        inactiveBorderColor = Color.Transparent,
                    ),
                    selected = selectedFilterItem.value == item,
                    onClick = {
                        if (selectedFilterItem.value != item)
                            selectedFilterItem.value = item
                        selectedFilterIndex.intValue = filterItems.indexOf(item)

                    },
                    icon = { }) {
                    Text(text = item, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

private fun LazyListScope.showInterviewList(
    interviewType: InterviewType,
    interviewListState: LazyListState,
    interviewList: InterviewList,
    dashboardViewModel: DashboardViewModel,
    interviewModel: InterviewViewModel,
    navController: NavHostController,
    openBottomSheet: MutableState<Boolean>
) {
    if (shouldLoadMore(
            interviewListState,
            interviewList.hasMore,
            dashboardViewModel.isLoading
        )
    ) {
        dashboardViewModel.loadMore(interviewType)
    }
    items(interviewList.list) { interview ->
        DashboardInterviewCard(
            interview = interview,
            onClick = { interviewModel.interviewData = interview },
            navController = navController,
            interviewType = interviewType,
            onInterviewStatusClicked = {
                interviewModel.interviewData = interview
                openBottomSheet.value = true
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowInterviewStatusBottomSheet(
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    scope: CoroutineScope,
    interviewModel: InterviewViewModel = hiltViewModel()
) {
    if (openBottomSheet.value) {
        IPBottomSheet(
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

private fun shouldLoadMore(
    listState: LazyListState,
    hasMore: Boolean,
    isLoading: Boolean
): Boolean {
    if (!hasMore) return false
    return listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ==
            listState.layoutInfo.totalItemsCount - 1 && !isLoading
}

@Preview
@Composable
fun InterviewTypeFilterPreview() {
    InterPrepTheme {
        LazyColumn(modifier = Modifier.padding(dimensionResource(id = R.dimen.dimension_8dp))) {
            item {
                InterviewTypeFilter(remember { mutableIntStateOf(0) })
            }
        }
    }
}

@Composable
fun rememberDirectionalLazyListState(
    lazyListState: LazyListState,
): DirectionalLazyListState {
    return remember {
        DirectionalLazyListState(lazyListState)
    }
}
