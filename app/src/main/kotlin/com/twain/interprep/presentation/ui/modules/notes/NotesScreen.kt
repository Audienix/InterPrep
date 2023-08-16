package com.twain.interprep.presentation.ui.modules.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.FullScreenEmptyState
import com.twain.interprep.presentation.ui.components.generic.IPAlertDialog
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.components.note.NoteCard
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun NotesScreen(
    navController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val showDeleteAlert = remember { mutableStateOf(false) }
    val deleteAlertTitleRes = remember { mutableIntStateOf(-1) }
    val deleteAlertContentRes = remember { mutableIntStateOf(-1) }
    val isNoteDeleteAlert = remember { mutableStateOf(false) }
    val interviewForDeletion = remember{ mutableStateOf(Interview()) }
    LaunchedEffect(Unit) {
        viewModel.getAllPastInterviewsWithNotes()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {  IPAppBar(stringResource(id = R.string.nav_item_notes))},
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize().padding(padding)
                    .background(MaterialColorPalette.surface)
            ) {

                if (viewModel.interviewNotesPair is ViewResult.Loaded) {
                    val interviewNotePairs =
                        (viewModel.interviewNotesPair as ViewResult.Loaded<List<Pair<Interview, List<Note>>>>).data
                    if (interviewNotePairs.isEmpty()) {
                        FullScreenEmptyState(
                            Modifier,
                            R.drawable.empty_state_notes,
                            stringResource(id = R.string.empty_state_title_note),
                            stringResource(id = R.string.empty_state_description_note)
                        )
                    }
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimension_8dp)))
                    IPHeader(
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(id = R.dimen.dimension_16dp),
                            vertical = dimensionResource(id = R.dimen.dimension_8dp)
                        ),
                        text = stringResource(id = R.string.header_view_note)
                    )
                    LazyColumn {
                        val deleteInterviewTitle = R.string.alert_dialog_delete_interview_title
                        val deleteInterviewContent = R.string.alert_dialog_delete_interview_text
                        val deleteNoteTitle = R.string.alert_dialog_delete_note_title
                        val deleteNoteContent = R.string.alert_dialog_delete_note_text
                        items(interviewNotePairs) { (interview, notes) ->
                            NoteCard(
                                interviewNotePair = interview to notes,
                                onViewNoteClick = {
                                    navController.navigate(
                                        AppScreens.MainScreens.ViewNotes.withArgs(
                                            interview.interviewId
                                        )
                                    ) {
                                        popUpTo(AppScreens.MainScreens.Notes.route)
                                    }
                                },
                                onAddNoteClick = {
                                    navController.navigate(
                                        AppScreens.MainScreens.AddNotes.withArgs(
                                            interview.interviewId,
                                            false
                                        )
                                    ) {
                                        popUpTo(AppScreens.MainScreens.Notes.route)
                                    }
                                },
                                onDeleteNotesForInterviewClick = {
                                    showDeleteAlert.value = true
                                    interviewForDeletion.value = interview
                                    deleteAlertTitleRes.intValue = deleteNoteTitle
                                    deleteAlertContentRes.intValue = deleteNoteContent
                                    isNoteDeleteAlert.value = true
                                }
                            ) {
                                showDeleteAlert.value = true
                                interviewForDeletion.value = interview
                                deleteAlertTitleRes.intValue = deleteInterviewTitle
                                deleteAlertContentRes.intValue = deleteInterviewContent
                                isNoteDeleteAlert.value = false
                            }
                        }
                    }
                    ShowDeleteConfirmationDialog(
                        showDeleteAlert,
                        deleteAlertTitleRes.intValue,
                        deleteAlertContentRes.intValue,
                        onDeleteConfirmed = {
                            showDeleteAlert.value = false
                            if(isNoteDeleteAlert.value)
                                viewModel.deleteNotesForInterview(interviewForDeletion.value)
                            else
                                viewModel.deleteInterview(interviewForDeletion.value)
                        }
                    )
                }
            }
        },
        containerColor = MaterialColorPalette.surface
    )
}

@Composable
private fun ShowDeleteConfirmationDialog(
    showDeleteDialog: MutableState<Boolean>,
    title: Int,
    content: Int,
    onDeleteConfirmed: () -> Unit
) {
    if (showDeleteDialog.value) {
        IPAlertDialog(
            titleResId = title,
            contentResId = content,
            // "OK" is clicked
            onPositiveButtonClick = onDeleteConfirmed,
            // "CANCEL" is clicked
            onNegativeButtonClick = {
                showDeleteDialog.value = false
            },
        )
    }
}
