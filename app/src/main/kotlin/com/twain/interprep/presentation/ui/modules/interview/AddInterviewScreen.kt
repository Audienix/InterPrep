package com.twain.interprep.presentation.ui.modules.interview

import android.Manifest
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.twain.interprep.R
import com.twain.interprep.constants.NumberConstants
import com.twain.interprep.constants.NumberConstants.MINUTE_IN_SECONDS
import com.twain.interprep.constants.StringConstants.NOTIFICATION_KEY_MESSAGE
import com.twain.interprep.constants.StringConstants.NOTIFICATION_KEY_TITLE
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.getInterviewField
import com.twain.interprep.data.model.isPast
import com.twain.interprep.data.model.isValid
import com.twain.interprep.data.ui.InterviewFormData.interviewFormList
import com.twain.interprep.notification.NotificationReminderWorker
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.CheckRuntimePermission
import com.twain.interprep.presentation.ui.components.generic.DeleteIcon
import com.twain.interprep.presentation.ui.components.generic.IPAlertDialog
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.components.generic.IPTextInput
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.utils.DateUtils
import java.util.concurrent.TimeUnit

@Composable
fun AddInterviewScreen(
    navController: NavHostController,
    viewModel: InterviewViewModel = hiltViewModel(),
    interviewId: Int
) {
    val context = LocalContext.current
    val isEditInterview = interviewId != 0
    val showBackConfirmationAlert = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }
    // Flag to check if we should highlight any empty mandatory input field by showing an error message
    val isBackPressed = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.interviewData = Interview()
        if (isEditInterview) {
            viewModel.getInterviewById(interviewId)
        }
    }

    val notification by viewModel.notification.collectAsState()
    val notificationTime by viewModel.notificationTime.collectAsState()
    BackHandler {
        handleBackPress(
            context,
            viewModel,
            isEditInterview,
            navController,
            showBackConfirmationAlert,
            notificationTime
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            IPAppBar(
                title = stringResource(id = R.string.appbar_title_edit_interview.takeIf { isEditInterview }
                    ?: R.string.appbar_title_add_interview),
                navIcon = {
                    IPIcon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = MaterialColorPalette.onSurfaceVariant
                    ) {
                        handleBackPress(
                            context,
                            viewModel,
                            isEditInterview,
                            navController,
                            showBackConfirmationAlert,
                            notificationTime
                        )
                    }
                },
                actions = {
                    if (isEditInterview) DeleteIcon { showDeleteDialog.value = true }
                }
            )
        },
        content = { padding ->
            ShowBackConfirmationDialog(showBackConfirmationAlert, navController, isBackPressed)
            ShowDeleteConfirmationDialog(showDeleteDialog, navController)
            ShowAddInterviewScreenContent(padding, isEditInterview, isBackPressed)
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
        },
        containerColor = MaterialColorPalette.surface
    )
}

fun handleBackPress(
    context: Context,
    viewModel: InterviewViewModel,
    isEditInterview: Boolean,
    navController: NavHostController,
    showBackConfirmationDialog: MutableState<Boolean>,
    minutes: Int,
) {
    val interviewData = viewModel.interviewData
    if (interviewData.isValid()) {
        // Save the interview details in local database
        viewModel.onSaveInterview(isEditInterview)
        // We should only create notification for future interviews
        if (!viewModel.interviewData.isPast() && minutes != 0) {
            val interviewTag = "interview_${interviewData.interviewId}"
            WorkManager.getInstance(context).cancelAllWorkByTag(interviewTag)
            Log.d("WorkManager", "Cancelled work with tag: $interviewTag")
            val notificationWorkRequest = viewModel.createInterviewNotification(
                title = context.resources.getString(R.string.notification_reminder_title),
                message = context.resources.getString(
                    R.string.notification_reminder_description,
                    interviewData.company,
                    minutes,
                ),
                reminderTimeBefore = MINUTE_IN_SECONDS * minutes,
                tag = interviewTag
            )
            WorkManager.getInstance(context).enqueue(notificationWorkRequest)
            Log.d("WorkManager", "Enqueued work with tag: $interviewTag")
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
    isBackPressed: MutableState<Boolean>,
    viewModel: InterviewViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        IPHeader(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.dimension_16dp)),
            text = stringResource(id = R.string.header_add_interview.takeUnless { isEditInterview }
                ?: R.string.header_edit_interview),
            textStyle = MaterialTheme.typography.bodyLarge,
            textColor = MaterialColorPalette.onSurface
        )
        Column(
            modifier = Modifier
                .background(color = MaterialColorPalette.surfaceContainerLow)
                .padding(
                    start = dimensionResource(id = R.dimen.dimension_16dp),
                    end = dimensionResource(id = R.dimen.dimension_16dp),
                    top = dimensionResource(id = R.dimen.dimension_16dp),
                    bottom = dimensionResource(id = R.dimen.dimension_8dp)
                )
        ) {
            InterviewSectionHeader()
            InterviewSectionContent(viewModel, isBackPressed)
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_16dp)))
        Column(
            modifier = Modifier
                .background(color = MaterialColorPalette.surfaceContainerLow)
                .padding(
                    start = dimensionResource(id = R.dimen.dimension_16dp),
                    end = dimensionResource(id = R.dimen.dimension_16dp),
                    top = dimensionResource(id = R.dimen.dimension_16dp),
                    bottom = dimensionResource(id = R.dimen.dimension_8dp)
                )
        ) {
            CompanySectionHeader()
            CompanySectionContent(viewModel, isBackPressed)
        }
    }
}

@Composable
private fun CompanySectionContent(
    viewModel: InterviewViewModel,
    isBackPressed: MutableState<Boolean>
) {
    IPTextInput(
        modifier = Modifier
            .fillMaxWidth(),
        inputText = viewModel.interviewData.getInterviewField(interviewFormList[7].labelTextId),
        textInputAttributes = interviewFormList[7],
        isBackPressed = isBackPressed.value,
        onTextUpdate = {
            isBackPressed.value = false
            viewModel.updateInterviewField(interviewFormList[7].labelTextId, it)
        }
    )
    IPTextInput(
        modifier = Modifier
            .fillMaxWidth(),
        inputText = viewModel.interviewData.getInterviewField(interviewFormList[8].labelTextId),
        textInputAttributes = interviewFormList[8],
        isBackPressed = isBackPressed.value,
        onTextUpdate = {
            isBackPressed.value = false
            viewModel.updateInterviewField(interviewFormList[8].labelTextId, it)
        }
    )
    IPTextInput(
        modifier = Modifier
            .fillMaxWidth(),
        inputText = viewModel.interviewData.getInterviewField(interviewFormList[9].labelTextId),
        textInputAttributes = interviewFormList[9],
        isBackPressed = isBackPressed.value,
        onTextUpdate = {
            isBackPressed.value = false
            viewModel.updateInterviewField(interviewFormList[9].labelTextId, it)
        }
    )
}

@Composable
private fun InterviewSectionContent(
    viewModel: InterviewViewModel,
    isBackPressed: MutableState<Boolean>
) {
    InterviewSectionDateTimeRow(viewModel, isBackPressed)
    IPTextInput(
        modifier = Modifier
            .fillMaxWidth(),
        inputText = viewModel.interviewData.getInterviewField(interviewFormList[2].labelTextId),
        textInputAttributes = interviewFormList[2],
        isBackPressed = isBackPressed.value,
        onTextUpdate = {
            isBackPressed.value = false
            viewModel.updateInterviewField(interviewFormList[2].labelTextId, it)
        }
    )
    IPTextInput(
        modifier = Modifier
            .fillMaxWidth(),
        inputText = viewModel.interviewData.getInterviewField(interviewFormList[3].labelTextId),
        textInputAttributes = interviewFormList[3],
        isBackPressed = isBackPressed.value,
        onTextUpdate = {
            isBackPressed.value = false
            viewModel.updateInterviewField(interviewFormList[3].labelTextId, it)
        }
    )
    IPTextInput(
        modifier = Modifier
            .fillMaxWidth(),
        inputText = viewModel.interviewData.getInterviewField(interviewFormList[4].labelTextId),
        textInputAttributes = interviewFormList[4],
        isBackPressed = isBackPressed.value,
        onTextUpdate = {
            isBackPressed.value = false
            viewModel.updateInterviewField(interviewFormList[4].labelTextId, it)
        }
    )
    IPTextInput(
        modifier = Modifier
            .fillMaxWidth(),
        inputText = viewModel.interviewData.getInterviewField(interviewFormList[5].labelTextId),
        textInputAttributes = interviewFormList[5],
        isBackPressed = isBackPressed.value,
        onTextUpdate = {
            isBackPressed.value = false
            viewModel.updateInterviewField(interviewFormList[5].labelTextId, it)
        }
    )
    IPTextInput(
        modifier = Modifier
            .fillMaxWidth(),
        inputText = viewModel.interviewData.getInterviewField(interviewFormList[6].labelTextId),
        textInputAttributes = interviewFormList[6],
        isBackPressed = isBackPressed.value,
        onTextUpdate = {
            isBackPressed.value = false
            viewModel.updateInterviewField(interviewFormList[6].labelTextId, it)
        }
    )
}

@Composable
private fun InterviewSectionDateTimeRow(
    viewModel: InterviewViewModel,
    isBackPressed: MutableState<Boolean>
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.dimension_8dp)
        )
    ) {
        IPTextInput(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            inputText = viewModel.interviewData.getInterviewField(interviewFormList[0].labelTextId),
            textInputAttributes = interviewFormList[0],
            isBackPressed = isBackPressed.value,
            onTextUpdate = {
                isBackPressed.value = false
                viewModel.updateInterviewField(interviewFormList[0].labelTextId, it)
            }
        )
        IPTextInput(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            inputText = viewModel.interviewData.getInterviewField(interviewFormList[1].labelTextId),
            textInputAttributes = interviewFormList[1],
            isBackPressed = isBackPressed.value,
            onTextUpdate = {
                isBackPressed.value = false
                viewModel.updateInterviewField(interviewFormList[1].labelTextId, it)
            }
        )
    }
}

@Composable
private fun InterviewSectionHeader() {
    IPHeader(
        text = stringResource(id = R.string.label_interview_details),
        textStyle = MaterialTheme.typography.titleMedium,
        textColor = MaterialColorPalette.onSurface,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth(),
        thickness = dimensionResource(id = R.dimen.dimension_stroke_width_low),
        color = MaterialColorPalette.outlineVariant
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
}

@Composable
private fun CompanySectionHeader() {
    IPHeader(
        text = stringResource(id = R.string.label_company_details),
        textStyle = MaterialTheme.typography.titleMedium,
        textColor = MaterialColorPalette.onSurface,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth(),
        thickness = dimensionResource(id = R.dimen.dimension_stroke_width_low),
        color = MaterialColorPalette.outlineVariant
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
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
            // "OK" is clicked
            onPositiveButtonClick = {
                viewModel.deleteInterview(viewModel.interviewData)
                showDeleteDialog.value = false
                navController.popBackStack(AppScreens.MainScreens.Dashboard.route, false)
            },
            // "CANCEL" is clicked
            onNegativeButtonClick = {
                showDeleteDialog.value = false
            },
        )
    }
}

@Composable
private fun ShowBackConfirmationDialog(
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
