package com.twain.interprep.presentation.ui.modules.dashboard

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.data.model.getInterviewField
import com.twain.interprep.data.model.isValid
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.components.interview.IPTextInput
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.ui.InterviewFormData.Companion.textTextInputHorizontalListAttributes
import com.twain.interprep.data.ui.InterviewFormData.Companion.textTextInputVerticalListAttributes
import com.twain.interprep.presentation.ui.modules.interview.InterviewViewModel

@Composable
fun AddInterviewScreen(
    navController: NavHostController,
    viewModel: InterviewViewModel = hiltViewModel()
) {
    val showDialog = remember { mutableStateOf(false) }

    // this ms controls if we should highlight any empty mandatory input field
    // by showing an error message
    val shouldValidate = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.interviewData = Interview()
    }
    BackHandler {
        if (viewModel.interviewData.isValid())
            viewModel.insertInterview(viewModel.interviewData)

        navController.popBackStack()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            IPAppBar(stringResource(id = R.string.appbar_title_add_interview)) {
                IconButton(onClick = {
                    if (viewModel.interviewData.isValid()) {
                        viewModel.insertInterview(viewModel.interviewData)
                        navController.popBackStack()
                    } else {
                        showDialog.value = true
                    }
                }) {
                    Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                }
            }
        },
        content = { padding ->
            if (showDialog.value) {
                IPAlertDialog(
                    titleResId = R.string.alert_dialog_unsaved_interview_title,
                    contentResId = R.string.alert_dialog_unsaved_interview_text,
                    onPositiveButtonClick = {
                        showDialog.value = false
                        navController.popBackStack()
                    }, // "OK" is clicked
                    onNegativeButtonClick = {
                        showDialog.value = false
                        shouldValidate.value = true
                    } // "CANCEL" is clicked
                )
            }

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
                    textTextInputHorizontalListAttributes.map { input ->
                        IPTextInput(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            inputText = viewModel.interviewData.getInterviewField(input.labelTextId),
                            textInputAttributes = input,
                            shouldValidate = shouldValidate.value,
                            onTextUpdate = {
                                viewModel.updateInterviewField(input.labelTextId, it)
                            }
                        )
                    }
                }
                textTextInputVerticalListAttributes.map { input ->
                    IPTextInput(
                        modifier = Modifier.fillMaxWidth(),
                        inputText = viewModel.interviewData.getInterviewField(input.labelTextId),
                        textInputAttributes = input,
                        shouldValidate = shouldValidate.value,
                        onTextUpdate = {
                            viewModel.updateInterviewField(input.labelTextId, it)
                        }
                    )
                }
            }
        }
    )
}
