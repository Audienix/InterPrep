package com.twain.interprep.presentation.ui.modules.notes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.presentation.ui.components.generic.FullScreenEmptyState
import com.twain.interprep.presentation.ui.components.generic.IPAlertDialog
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.components.generic.IPOutlinedButton
import com.twain.interprep.presentation.ui.components.note.AddNoteCard
import com.twain.interprep.presentation.ui.components.note.InterviewDetailForNote
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple
import com.twain.interprep.presentation.ui.theme.BackgroundSurface

@Composable
fun AddNotesScreen(
    navController: NavController,
    interviewId: Int,
    isEdit: Boolean,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val shouldShowAlert = remember { mutableStateOf(false) }
    // Flag to check if we should highlight any empty mandatory input field by showing an error message
    var shouldValidateFormFields by remember { mutableStateOf(false) }

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
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            topBar = {
                IPAppBar(
                    title = stringResource(
                        id = R.string.appbar_title_add_notes.takeUnless { isEdit }
                            ?: R.string.appbar_title_edit_notes),
                    navIcon = {
                        IPIcon(imageVector = Icons.Filled.ArrowBack, tint = Color.White) {
                            handleBackPress(viewModel, navController, shouldShowAlert)
                        }
                    }
                )
            },
            content = { padding ->
                if (shouldShowAlert.value) {
                    IPAlertDialog(
                        titleResId = R.string.alert_dialog_unsaved_notes_title,
                        contentResId = R.string.alert_dialog_unsaved_notes_text,
                        // "OK" is clicked
                        onPositiveButtonClick = {
                            shouldShowAlert.value = false
                            navController.popBackStack()
                        },
                        // "CANCEL" is clicked
                        onNegativeButtonClick = {
                            shouldShowAlert.value = false
                            shouldValidateFormFields = true
                        })
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = padding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BackgroundSurface)
                    ) {
                        InterviewDetailForNote(
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.dimension_16dp)),
                            interview = interview,
                            shouldShowDeleteButton = false,
                            notesEmpty = viewModel.notes.isEmpty(),
                            {}
                        ) {
                            viewModel.deleteNotesForInterview(interview)
                        }
                    }
                    if (!viewModel.notes.isEmpty()) {
                        IPHeader(
                            stringResource(id = R.string.add_note_header.takeUnless { isEdit }
                                ?: R.string.edit_note_header),
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            MaterialTheme.typography.titleMedium,
                            Modifier.padding(
                                start = dimensionResource(id = R.dimen.dimension_16dp),
                                end = dimensionResource(id = R.dimen.dimension_16dp),
                                top = dimensionResource(id = R.dimen.dimension_16dp),
                                bottom = dimensionResource(id = R.dimen.dimension_4dp)
                            )
                                .align(Alignment.Start),
                            fontWeight = FontWeight.Normal
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
                                deleteNote = { viewModel.deleteNote(interview, note) },
                                deleteQuestion = { questionIndex ->
                                    viewModel.deleteQuestion(index,questionIndex)
                                },
                                shouldValidate = shouldValidateFormFields,
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
                                IPOutlinedButton(
                                    backgroundColor = BackgroundLightPurple,
                                    text = stringResource(id = R.string.add_note),
                                    textColor = Color.Black,
                                    textStyle = MaterialTheme.typography.titleMedium,
                                    enabled = viewModel.addNoteEnabled(),
                                    iconColor = BackgroundDarkPurple,
                                    borderColor = BackgroundDarkPurple,
                                    leadingIcon = R.drawable.outline_add_circle,
                                    onClick = { viewModel.addNote() })
                            }
                        }
                    }
                }
            })
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
