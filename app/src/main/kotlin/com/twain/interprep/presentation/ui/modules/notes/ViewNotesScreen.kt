package com.twain.interprep.presentation.ui.modules.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.EditIcon
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.note.InterviewDetailForNote
import com.twain.interprep.presentation.ui.components.note.ViewNoteCard
import com.twain.interprep.presentation.ui.theme.BackgroundSurface

@Composable
fun ViewNotesScreen(
    navController: NavController,
    interviewId: Int,
    viewModel: NotesViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.initAddNoteScreen(interviewId, true)
    }

    if (viewModel.interview is ViewResult.Loaded) {
        val interview = (viewModel.interview as ViewResult.Loaded<Interview>).data
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            topBar = {
                IPAppBar(
                    title = stringResource(id = R.string.appbar_title_view_notes),
                    navIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                        }
                    }
                ) {
                    EditIcon(tint = Color.White) {
                        navController.navigate(
                            AppScreens.AddNotes.withArgs(
                                interview.interviewId,
                                true
                            )
                        ) {
                            popUpTo(AppScreens.ViewNotes.route)
                        }
                    }
                }
            },
            content = { padding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .padding(bottom = dimensionResource(id = R.dimen.dimension_16dp))
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(BackgroundSurface)
                        ) {
                            InterviewDetailForNote(
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.dimension_16dp)),
                                interview = interview,
                                shouldShowDeleteButton = false
                            )
                        }
                    }
                    itemsIndexed(viewModel.notes, key = { _, note -> note.noteId }) { index, note ->
                        ViewNoteCard(
                            note = note, onEditClicked = {
                                navController.navigate(
                                    AppScreens.AddNotes.withArgs(
                                        interview.interviewId,
                                        true
                                    )
                                ) {
                                    popUpTo(AppScreens.ViewNotes.route)
                                }
                            },
                            onDeleteClicked = {
                                viewModel.deleteNote(interview, note)
                            },
                            index = index + 1
                        )
                    }
                }
            })
    }
}