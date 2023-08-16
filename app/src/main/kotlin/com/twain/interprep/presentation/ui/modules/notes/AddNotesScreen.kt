package com.twain.interprep.presentation.ui.modules.notes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.presentation.ui.components.generic.FullScreenEmptyState
import com.twain.interprep.presentation.ui.components.generic.IPAlertDialog
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPFilledButton
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.components.note.AddNoteCard
import com.twain.interprep.presentation.ui.components.note.InterviewDetailForNote
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun AddNotesScreen(
    navController: NavHostController,
    interviewId: Int,
    isEdit: Boolean,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val shouldShowAlert = remember { mutableStateOf(false) }
    // Flag to check if we should highlight any empty mandatory input field by showing an error message
    val shouldValidateFormFields = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getInterviewsWithNotesByInterviewId(interviewId, isEdit)
    }
    BackHandler {
        handleBackPress(viewModel, navController, shouldShowAlert)
    }
    if (viewModel.interview is ViewResult.Loaded) {
        val interview = (viewModel.interview as ViewResult.Loaded<Interview>).data
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                IPAppBar(
                    title = stringResource(
                        id = R.string.appbar_title_add_notes.takeUnless { isEdit }
                            ?: R.string.appbar_title_edit_notes),
                    navIcon = {
                        IPIcon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = MaterialColorPalette.onSurfaceVariant
                        ) {
                            handleBackPress(viewModel, navController, shouldShowAlert)
                        }
                    }
                )
            },
            content = { padding ->
                ShowBackConfirmationDialog(shouldShowAlert, navController, shouldValidateFormFields)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = padding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        InterviewDetailForNote(
                            modifier = Modifier.padding(
                                vertical = dimensionResource(id = R.dimen.dimension_8dp),
                                horizontal = dimensionResource(id = R.dimen.dimension_16dp)
                            ),
                            interview = interview,
                            shouldShowMenuOption = false,
                            notesEmpty = viewModel.notes.isEmpty(),
                            onDeleteInterview = {}
                        ) {
                            viewModel.deleteNotesForInterview(interview)
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth(),
                        thickness = dimensionResource(id = R.dimen.dimension_stroke_width_low),
                        color = MaterialColorPalette.outlineVariant
                    )
                    if (!viewModel.notes.isEmpty()) {
                        IPHeader(
                            text = stringResource(id = R.string.header_add_note.takeUnless { isEdit }
                                ?: R.string.header_edit_note),
                            modifier = Modifier
                                .padding(
                                    start = dimensionResource(id = R.dimen.dimension_16dp),
                                    end = dimensionResource(id = R.dimen.dimension_16dp),
                                    top = dimensionResource(id = R.dimen.dimension_16dp),
                                    bottom = dimensionResource(id = R.dimen.dimension_4dp)
                                )
                                .align(Alignment.Start)
                        )
                    } else {
                        FullScreenEmptyState(
                            modifier = Modifier.fillMaxHeight(),
                            R.drawable.empty_state_notes,
                            stringResource(id = R.string.empty_state_title_note),
                            stringResource(id = R.string.empty_state_description_note)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(bottom = dimensionResource(id = R.dimen.dimension_16dp))
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        viewModel.notes.forEachIndexed { index, note ->
                            AddNoteCard(
                                modifier = Modifier
                                    .padding(
                                        start = dimensionResource(id = R.dimen.dimension_12dp),
                                        end = dimensionResource(id = R.dimen.dimension_12dp),
                                        top = dimensionResource(id = R.dimen.dimension_16dp),
                                        bottom = dimensionResource(id = R.dimen.dimension_16dp)
                                    )
                                    .fillMaxWidth(),
                                note = note,
                                getNoteField = { viewModel.getNoteField(it, index) },
                                updateNoteField = { resId, value ->
                                    viewModel.updateNoteField(
                                        resId,
                                        index,
                                        value
                                    )
                                },
                                updateQuestion = { questionIndex, value ->
                                    viewModel.updateQuestion(
                                        index,
                                        questionIndex,
                                        value
                                    )
                                },
                                addQuestion = { viewModel.addQuestion(index) },
                                deleteNote = { viewModel.deleteNote(note) },
                                deleteQuestion = { questionIndex ->
                                    viewModel.deleteQuestion(index, questionIndex)
                                },
                                shouldValidate = shouldValidateFormFields.value,
                                isEdit
                            )
                        }
                        if (!isEdit) {
                            Row(
                                modifier = Modifier.padding(
                                    vertical = dimensionResource(id = R.dimen.dimension_12dp),
                                    horizontal = dimensionResource(id = R.dimen.dimension_16dp)
                                )
                            ) {
                                IPFilledButton(
                                    backgroundColor = MaterialColorPalette.primaryContainer,
                                    text = stringResource(id = R.string.button_add_note),
                                    textColor = MaterialColorPalette.onPrimaryContainer,
                                    textStyle = MaterialTheme.typography.labelLarge,
                                    enabled = viewModel.addNoteEnabled(),
                                    iconColor = MaterialColorPalette.onPrimaryContainer,
                                    leadingIcon = R.drawable.ic_outline_add_circle_24,
                                    onClick = { viewModel.addNote() })
                            }
                        }
                    }
                }
            },
            containerColor = MaterialColorPalette.surface
        )
    }
}

private fun handleBackPress(
    viewModel: NotesViewModel,
    navController: NavController,
    shouldShowAlert: MutableState<Boolean>
) {
    if (viewModel.areAllNotesValid()) {
        viewModel.saveNotes()
        navController.popBackStack()
    } else shouldShowAlert.value = true
}

@Composable
private fun ShowBackConfirmationDialog(
    shouldShowAlert: MutableState<Boolean>,
    navController: NavHostController,
    shouldValidateFormFields: MutableState<Boolean>
) {
    if (shouldShowAlert.value) {
        IPAlertDialog(
            titleResId = R.string.alert_dialog_unsaved_notes_title,
            contentResId = R.string.alert_dialog_unsaved_notes_text,
            // "OK" is clicked
            onPositiveButtonClick = {
                shouldShowAlert.value = false
                shouldValidateFormFields.value = false
                navController.popBackStack()
            },
            // "CANCEL" is clicked
            onNegativeButtonClick = {
                shouldShowAlert.value = false
                shouldValidateFormFields.value = true
            })
    }
}
