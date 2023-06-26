package com.twain.interprep.presentation.ui.modules.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import com.twain.interprep.presentation.ui.components.generic.DeleteIcon
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.components.generic.IPOutlinedButton
import com.twain.interprep.presentation.ui.components.note.AddNoteCard
import com.twain.interprep.presentation.ui.components.note.InterviewDetailForNote
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple

@Composable
fun AddNotesScreen(
    navController: NavController,
    interviewId: Int,
    viewModel: NotesViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.initAddNoteScreen(interviewId)
    }
    if (viewModel.interview is ViewResult.Loaded) {
        val interview = (viewModel.interview as ViewResult.Loaded<Interview>).data
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
                    DeleteIcon { }
                }
            },
            content = { padding ->

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp))
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    InterviewDetailForNote(interview = interview)
                    IPHeader(
                        stringResource(id = R.string.add_note_header),
                        MaterialTheme.colorScheme.onSurfaceVariant,
                        MaterialTheme.typography.titleMedium,
                        Modifier.padding(vertical = dimensionResource(id = R.dimen.dimension_16dp)),
                        fontWeight = FontWeight.Normal
                    )

                    viewModel.notes.forEachIndexed { index, note ->
                        AddNoteCard(
                            note = note,
                            getNoteField = { viewModel.getNoteField(it, index) },
                            updateNoteField = { resId, value -> viewModel.updateNoteField(resId, index, value) },
                            updateQuestion = { questionIndex, value -> viewModel.updateQuestion(index, questionIndex, value) },
                            addQuestion = { viewModel.addQuestion(index) }
                        )
                    }
                    Row(
                        modifier = Modifier.padding(
                            top = dimensionResource(id = R.dimen.dimension_12dp)
                        )
                    ) {
                        IPOutlinedButton(
                            backgroundColor = BackgroundLightPurple,
                            text = stringResource(id = R.string.add_note),
                            textColor = Color.Black,
                            textStyle = MaterialTheme.typography.titleMedium,
                            iconColor = BackgroundDarkPurple,
                            borderColor = BackgroundDarkPurple,
                            leadingIcon = R.drawable.outline_add_circle,
                            onClick = { viewModel.addNote(interviewId) })
                    }
                }
            })
    }
}
