package com.twain.interprep.presentation.ui.modules.interview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewType
import com.twain.interprep.data.model.isPast
import com.twain.interprep.data.ui.InterviewFormData
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.DeleteIcon
import com.twain.interprep.presentation.ui.components.generic.EditIcon
import com.twain.interprep.presentation.ui.components.generic.IPAlertDialog
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.components.generic.IPInterviewStatus
import com.twain.interprep.presentation.ui.components.interview.InterviewDetailsCard
import com.twain.interprep.presentation.ui.modules.dashboard.ShowInterviewStatusBottomSheet
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.utils.getInterviewCardColorPair

@Composable
fun InterviewDetailsScreen(
    navController: NavHostController,
    viewModel: InterviewViewModel = hiltViewModel(),
    interviewId: Int?,
    interviewType: InterviewType
) {
    val showDeleteDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.interviewData = Interview()
        interviewId?.let { viewModel.getInterviewById(id = it) }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            val array = stringArrayResource(id = R.array.interview_filter)
            IPAppBar(
                title = array[interviewType.ordinal],
                navIcon = {
                    IPIcon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = MaterialColorPalette.onSurfaceVariant
                    ) {
                        navController.popBackStack()
                    }
                }
            ) {
                EditIcon {
                    interviewId?.let {
                        navController.navigate(AppScreens.MainScreens.AddInterview.withArgs(it))
                    }
                }
                DeleteIcon { showDeleteDialog.value = true }
            }
        },
        content = { padding ->
            ShowDeleteConfirmationDialog(showDeleteDialog, navController)
            ShowInterviewDetailsScreenContent(padding, interviewType)
        },
        containerColor = MaterialColorPalette.surface
    )
}

@Composable
private fun ShowDeleteConfirmationDialog(
    showDeleteDialog: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: InterviewViewModel = hiltViewModel()
) {
    if (showDeleteDialog.value) {
        IPAlertDialog(
            titleResId = R.string.alert_dialog_delete_interview_title,
            contentResId = R.string.alert_dialog_delete_interview_text,
            onPositiveButtonClick = {
                showDeleteDialog.value = false
                viewModel.deleteInterview(viewModel.interviewData)
                navController.popBackStack()

            }, // "OK" is clicked
            onNegativeButtonClick = {
                showDeleteDialog.value = false
            } // "CANCEL" is clicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowInterviewDetailsScreenContent(
    padding: PaddingValues,
    interviewType: InterviewType
) {
    val scope = rememberCoroutineScope()
    val openBottomSheet = rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimension_4dp))
            .verticalScroll(rememberScrollState()),
    ) {
        ShowPastInterviewStatus(openBottomSheet = openBottomSheet)

        val colorPair = getInterviewCardColorPair(type = interviewType)
        ShowInterviewDetailsCard(colorPair = colorPair)
        ShowCompanyDetailsCard(colorPair = colorPair)
    }
    ShowInterviewStatusBottomSheet(openBottomSheet, bottomSheetState, scope)
}

@Composable
private fun ShowPastInterviewStatus(
    viewModel: InterviewViewModel = hiltViewModel(),
    openBottomSheet: MutableState<Boolean>
) {
    if (viewModel.interviewData.isPast()) {
        ShowInterviewStatus(viewModel, openBottomSheet)
    }
}

@Composable
private fun ShowInterviewStatus(
    viewModel: InterviewViewModel = hiltViewModel(),
    openBottomSheet: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.dimension_8dp),
                vertical = dimensionResource(id = R.dimen.dimension_16dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
    ) {
        Text(
            text = stringResource(R.string.label_status),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialColorPalette.onSurface
        )
        IPInterviewStatus(
            status = viewModel.interviewData.interviewStatus,
            onClick = { openBottomSheet.value = true }
        )
    }
}

@Composable
private fun ShowInterviewDetailsCard(
    colorPair: Pair<Color, Color>,
    viewModel: InterviewViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val labelList = InterviewFormData.getInterviewTextLabelList(viewModel.interviewData, context)

    InterviewDetailsCard(
        labelList = labelList,
        headerText = stringResource(id = R.string.label_interview_details),
        headerBackgroundColor = colorPair.first,
        headerContentColor = colorPair.second,
    )
}

@Composable
private fun ShowCompanyDetailsCard(
    colorPair: Pair<Color, Color>,
    viewModel: InterviewViewModel = hiltViewModel()
) {
    val labelList = InterviewFormData.getCompanyTextLabelList(viewModel.interviewData)
    InterviewDetailsCard(
        headerText = stringResource(id = R.string.label_company_details),
        headerContentColor = colorPair.second,
        headerBackgroundColor = colorPair.first,
        labelList = labelList
    )
}

@Preview
@Composable
fun InterviewDetailsScreenPreview() {
    InterviewDetailsScreen(
        navController = rememberNavController(),
        interviewId = 1,
        interviewType = InterviewType.FUTURE
    )
}
