package com.twain.interprep.presentation.ui.modules.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.FullScreenEmptyState
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.note.NoteCard

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getAllInterviewsWithNotes()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        IPAppBar(stringResource(id = R.string.nav_item_notes))

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
            LazyColumn {
                items(interviewNotePairs) { (interview, notes) ->
                    NoteCard(
                        interviewNotePair = interview to notes,
                        onDeleteNoteClick = {
                            viewModel.deleteNote(interview, it)
                        },
                        onViewNoteClick = {
                            navController.navigate(AppScreens.MainScreens.ViewNotes.withArgs(interview.interviewId)) {
                                popUpTo(AppScreens.MainScreens.Notes.route)
                            }
                        },
                        onAddNoteClick = {
                            navController.navigate(AppScreens.MainScreens.AddNotes.withArgs(interview.interviewId, false)) {
                                popUpTo(AppScreens.MainScreens.Notes.route)
                            }
                        },
                        onDeleteNotesForInterviewClick = {
                            viewModel.deleteNotesForInterview(interview)
                        },
                        onDeleteInterviewClick = {
                            viewModel.deleteInterview(interview)
                        }
                    )
                }
            }
        }
    }
}
