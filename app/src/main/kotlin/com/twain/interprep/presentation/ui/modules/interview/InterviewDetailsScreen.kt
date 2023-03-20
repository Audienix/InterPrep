package com.twain.interprep.presentation.ui.modules.interview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.twain.interprep.data.ui.QuoteData
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.DeleteIcon
import com.twain.interprep.presentation.ui.components.generic.IPAlertDialog
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPQuoteCard
import com.twain.interprep.presentation.ui.components.interview.IPInterviewDetailsCard
import com.twain.interprep.presentation.ui.components.interview.InterviewBottomSheet
import com.twain.interprep.presentation.ui.components.interview.IPInterviewStatus
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple
import kotlinx.coroutines.launch
import kotlin.random.Random.Default.nextInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewDetailsScreen(
    navController: NavHostController,
    viewModel: InterviewViewModel = hiltViewModel(),
    quotesViewModel: QuotesViewModel = hiltViewModel(),
    interviewId: Int?,
    primaryColor: Color,
    secondaryColor: Color
) {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        viewModel.interviewData = Interview()
        interviewId?.let { viewModel.getInterviewById(id = it) }
        quotesViewModel.getQuotes()
    }

    var showDeleteDialog by remember { mutableStateOf(false) }
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
                DeleteIcon { showDeleteDialog = true }
            }
        },
        content = { padding ->
            if (showDeleteDialog) {
                IPAlertDialog(
                    titleResId = R.string.alert_dialog_delete_interview_title,
                    contentResId = R.string.alert_dialog_delete_interview_text,
                    onPositiveButtonClick = {
                        showDeleteDialog = false
                        viewModel.deleteInterview(viewModel.interviewData)
                        navController.popBackStack()

                    }, // "OK" is clicked
                    onNegativeButtonClick = {
                        showDeleteDialog = false
                    } // "CANCEL" is clicked
                )
            }
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.dimension_4dp))
                    .verticalScroll(rememberScrollState()),
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
                            onClick = { openBottomSheet = true }
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
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
                }
                IPInterviewDetailsCard(
                    interview = viewModel.interviewData,
                    headerColor = secondaryColor,
                    onEditClick = {
                        interviewId?.let {
                            navController.navigate(AppScreens.AddInterview.withArgs(it))
                        }
                    })
            }
        }
    )
    if (openBottomSheet) {
        InterviewBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            bottomSheetState = bottomSheetState,
            onNewStatusSelected = {
                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        openBottomSheet = false
                    }
                }
                viewModel.upDateInterviewStatus(it)
            },
            highlightedStatus = viewModel.interviewData.interviewStatus
        )
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