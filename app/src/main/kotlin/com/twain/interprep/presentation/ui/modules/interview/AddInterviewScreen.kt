package com.twain.interprep.presentation.ui.modules.interview

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.work.WorkManager
import com.twain.interprep.R
import com.twain.interprep.constants.NumberConstants.MINUTES
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.getInterviewField
import com.twain.interprep.data.model.isPast
import com.twain.interprep.data.model.isValid
import com.twain.interprep.data.ui.InterviewFormData.textInputHorizontalList
import com.twain.interprep.data.ui.InterviewFormData.textInputVerticalList
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.CheckRuntimePermission
import com.twain.interprep.presentation.ui.components.generic.DeleteIcon
import com.twain.interprep.presentation.ui.components.generic.IPAlertDialog
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.components.generic.IPTextInput

@Composable
fun AddInterviewScreen(
    navController: NavHostController,
    viewModel: InterviewViewModel = hiltViewModel(),
    interviewId: Int
) {
    val context = LocalContext.current
    val isEditInterview = interviewId != 0
    val showBackConfirmationDialog = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }
    // Flag to check if we should highlight any empty mandatory input field by showing an error message
    val shouldValidateFormFields = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.interviewData = Interview()
        if (isEditInterview) {
            viewModel.getInterviewById(interviewId)
        }
    }
    BackHandler {
        handleBackPress(
            context,
            viewModel,
            isEditInterview,
            navController,
            showBackConfirmationDialog
        )
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            IPAppBar(
                title = stringResource(id = R.string.appbar_title_edit_interview.takeIf { isEditInterview }
                    ?: R.string.appbar_title_add_interview),
                navIcon = {
                    IPIcon(imageVector = Icons.Filled.ArrowBack, tint = Color.White) {
                        handleBackPress(
                            context,
                            viewModel,
                            isEditInterview,
                            navController,
                            showBackConfirmationDialog
                        )
                    }
                },
                actions = {
                    if (isEditInterview) DeleteIcon { showDeleteDialog.value = true }
                }
            )
        },
        content = { padding ->
            ShowConfirmationDialog(
                showBackConfirmationDialog,
                navController,
                shouldValidateFormFields
            )

            ShowDeleteConfirmationDialog(showDeleteDialog, navController, viewModel)

            ShowAddInterviewScreenContent(
                padding,
                isEditInterview,
                viewModel,
                shouldValidateFormFields
            )
            // In Android 13+ , we require user consent for posting notification.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                CheckRuntimePermission(
                    onPermissionGranted = {},
                    onPermissionDenied = {
                        //TODO show an alert dialog that user won't get notification of scheduled interview
                    },
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    )
}


private fun handleBackPress(
    context: Context,
    viewModel: InterviewViewModel,
    isEditInterview: Boolean,
    navController: NavHostController,
    showBackConfirmationDialog: MutableState<Boolean>
) {
    val interviewData = viewModel.interviewData
    if (interviewData.isValid()) {
        //Save the interview details in local database
        viewModel.onSaveInterview(isEditInterview)

        // We should only create notification for future interviews
        if (!viewModel.interviewData.isPast()) {
            val notificationWorkRequest = viewModel.createInterviewNotification(
                title = context.resources.getString(R.string.noti_interview_reminder_title),
                message = context.resources.getString(
                    R.string.noti_interview_reminder_description,
                    interviewData.company
                ),
                reminderTimeBefore = MINUTES
            )
            WorkManager.getInstance(context).enqueue(notificationWorkRequest)
        }

        navController.popBackStack()
    } else {
        showBackConfirmationDialog.value = true
    }
}

@Composable
private fun ShowAddInterviewScreenContent(
    padding: PaddingValues,
    isEditInterview: Boolean,
    viewModel: InterviewViewModel,
    shouldValidateFormFields: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        IPHeader(
            stringResource(id = R.string.add_interview_header.takeUnless { isEditInterview }
                ?: R.string.edit_interview_header),
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
            textInputHorizontalList.map { input ->
                IPTextInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    inputText = viewModel.interviewData.getInterviewField(input.labelTextId),
                    textInputAttributes = input,
                    shouldValidate = shouldValidateFormFields.value,
                    onTextUpdate = {
                        viewModel.updateInterviewField(input.labelTextId, it)
                    }
                )
            }
        }
        textInputVerticalList.map { input ->
            IPTextInput(
                modifier = Modifier.fillMaxWidth(),
                inputText = viewModel.interviewData.getInterviewField(input.labelTextId),
                textInputAttributes = input,
                shouldValidate = shouldValidateFormFields.value,
                onTextUpdate = {
                    viewModel.updateInterviewField(input.labelTextId, it)
                }
            )
        }
    }
}

@Composable
private fun ShowDeleteConfirmationDialog(
    showDeleteDialog: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: InterviewViewModel
) {
    if (showDeleteDialog.value) {
        IPAlertDialog(
            titleResId = R.string.alert_dialog_delete_interview_title,
            contentResId = R.string.alert_dialog_delete_interview_text,
            // "OK" is clicked
            onPositiveButtonClick = {
                showDeleteDialog.value = false
                viewModel.deleteInterview(viewModel.interviewData)
                navController.popBackStack(AppScreens.Dashboard.route, false)
            },
            // "CANCEL" is clicked
            onNegativeButtonClick = {
                showDeleteDialog.value = false
            },
        )
    }
}

@Composable
private fun ShowConfirmationDialog(
    showConfirmationDialog: MutableState<Boolean>,
    navController: NavHostController,
    shouldValidateFormFields: MutableState<Boolean>
) {
    if (showConfirmationDialog.value) {
        IPAlertDialog(
            titleResId = R.string.alert_dialog_unsaved_interview_title,
            contentResId = R.string.alert_dialog_unsaved_interview_text,
            // "OK" is clicked
            onPositiveButtonClick = {
                showConfirmationDialog.value = false
                navController.popBackStack()
            },
            // "CANCEL" is clicked
            onNegativeButtonClick = {
                showConfirmationDialog.value = false
                shouldValidateFormFields.value = true
            }
        )
    }
}
