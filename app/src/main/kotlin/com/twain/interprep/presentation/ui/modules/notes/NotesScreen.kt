package com.twain.interprep.presentation.ui.modules.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.presentation.ui.components.generic.FullScreenEmptyState
import com.twain.interprep.presentation.ui.components.generic.IPAppBar

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit){
        viewModel.getNoteInterviewPairs()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        IPAppBar(stringResource(id = R.string.nav_item_notes))

        if (viewModel.interviewNotesPair is ViewResult.Loaded){

            val interviews = (viewModel.interviewNotesPair as ViewResult.Loaded<List<Pair<Interview, List<Note>>>>).data
            if (interviews.isEmpty()) {
                FullScreenEmptyState(
                    Modifier,
                    R.drawable.empty_state_notes,
                    stringResource(id = R.string.empty_state_title_note),
                    stringResource(id = R.string.empty_state_description_note)
                )
            }
            interviews.forEach {
                Text(text = it.first.company)
            }
        }
    }
}
