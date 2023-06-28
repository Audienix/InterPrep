package com.twain.interprep.presentation.ui.modules.interview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.isPast
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.DeleteIcon
import com.twain.interprep.presentation.ui.components.generic.IPAlertDialog
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPQuoteCard
import com.twain.interprep.presentation.ui.components.interview.IPInterviewDetailsCard
import com.twain.interprep.presentation.ui.components.interview.IPInterviewStatus
import com.twain.interprep.presentation.ui.modules.dashboard.ShowInterviewStatusBottomSheet
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple
import kotlin.random.Random.Default.nextInt

@Composable
fun InterviewDetailsScreen(
    navController: NavHostController,
    viewModel: InterviewViewModel = hiltViewModel(),
    quotesViewModel: QuotesViewModel = hiltViewModel(),
    interviewId: Int?,
    primaryColor: Color,
    secondaryColor: Color
) {
    val showDeleteDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.interviewData = Interview()
        interviewId?.let { viewModel.getInterviewById(id = it) }
        quotesViewModel.getQuotes()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            IPAppBar(
                title = stringResource(id = R.string.appbar_title_interview_details),
                navIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                    }
                }
            ) {
                DeleteIcon { showDeleteDialog.value = true }
            }
        },
        content = { padding ->
            ShowDeleteConfirmationDialog(showDeleteDialog, viewModel, navController)
            ShowInterviewDetailsScreenContent(
                padding,
                viewModel,
                quotesViewModel,
                primaryColor,
                secondaryColor,
                interviewId,
                navController
            )
        }
    )
}

@Composable
private fun ShowDeleteConfirmationDialog(
    showDeleteDialog: MutableState<Boolean>,
    viewModel: InterviewViewModel,
    navController: NavHostController
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
    viewModel: InterviewViewModel,
    quotesViewModel: QuotesViewModel,
    primaryColor: Color,
    secondaryColor: Color,
    interviewId: Int?,
    navController: NavHostController
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
        ShowQuoteHeader(viewModel, openBottomSheet, quotesViewModel, primaryColor)
        ShowInterviewDetailsCard(viewModel, secondaryColor, interviewId, navController)
    }
    ShowInterviewStatusBottomSheet(openBottomSheet, bottomSheetState, scope, viewModel)
}

@Composable
private fun ShowInterviewDetailsCard(
    viewModel: InterviewViewModel,
    secondaryColor: Color,
    interviewId: Int?,
    navController: NavHostController
) {
    IPInterviewDetailsCard(
        interview = viewModel.interviewData,
        headerColor = secondaryColor,
        onEditClick = {
            interviewId?.let {
                navController.navigate(AppScreens.AddInterview.withArgs(it))
            }
        })
}

@Composable
private fun ShowQuoteHeader(
    viewModel: InterviewViewModel,
    openBottomSheet: MutableState<Boolean>,
    quotesViewModel: QuotesViewModel,
    primaryColor: Color
) {
    if (viewModel.interviewData.isPast()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.dimension_8dp),
                    vertical = dimensionResource(id = R.dimen.dimension_16dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Status: ", style = MaterialTheme.typography.titleMedium)
            IPInterviewStatus(
                status = viewModel.interviewData.interviewStatus,
                onClick = { openBottomSheet.value = true }
            )
        }
    } else {
        val quotes = quotesViewModel.quotesList
        if (quotes.isNotEmpty()) {
            IPQuoteCard(
                quote = quotes[nextInt
                    (0, quotes.size)],
                backgroundColor = primaryColor
            )
        }
    }
}

@Preview
@Composable
fun InterviewDetailsScreenPreview() {
    InterviewDetailsScreen(
        navController = rememberNavController(),
        interviewId = 1,
        primaryColor = BackgroundLightPurple,
        secondaryColor = BackgroundDarkPurple
    )
}