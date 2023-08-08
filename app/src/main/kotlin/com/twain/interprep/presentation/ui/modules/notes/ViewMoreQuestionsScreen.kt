package com.twain.interprep.presentation.ui.modules.notes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun ViewMoreQuestionsScreen(
    viewModel: QuestionViewModel = hiltViewModel(),
    noteId: Int,
    noteIndex: Int
) {
    LaunchedEffect(Unit) {
        viewModel.getNote(noteId)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialColorPalette.surface)
    ) {
        item {
            IPAppBar(
                title = stringResource(id = R.string.label_question),
                navIcon = {
                    IPIcon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = MaterialColorPalette.onSurfaceVariant
                    ) {

                    }
                }
            )
        }
        item {
            NoteDetails(
                noteIndex = noteIndex,
                interviewSegment = viewModel.note.interviewSegment,
                topic = viewModel.note.topic
            )
        }

        itemsIndexed(items = viewModel.note.questions) {index, item ->

            Question(
                questionIndex = index + 1,
                question = item,
                isLast = index == viewModel.note.questions.lastIndex
            )

            if (index == viewModel.note.questions.lastIndex)
                Spacer(modifier = Modifier.padding(
                    bottom = dimensionResource(id = R.dimen.dimension_16dp)))
        }
    }
}

@Composable
fun NoteDetails(
    modifier: Modifier = Modifier,
    noteIndex: Int,
    interviewSegment: String,
    topic: String
) {
    Row(
        modifier = modifier
            .background(MaterialColorPalette.surfaceContainer)
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.dimension_16dp)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_16dp))
    ) {
        NoteIndex(index = noteIndex)
        NoteHeader(interviewSegment = interviewSegment, topic = topic)
    }
}

@Composable
fun NoteIndex(
    modifier: Modifier = Modifier,
    index: Int
) {
    Card(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.dimension_icon_size_large)),
        border = BorderStroke(
            dimensionResource(id = R.dimen.dimension_stroke_width_low),
            MaterialColorPalette.onSecondaryContainer
        ),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialColorPalette.secondaryContainer
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = index.toString(),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun NoteHeader(
    modifier: Modifier = Modifier,
    interviewSegment: String,
    topic: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
    ) {
        Text(
            text = interviewSegment,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialColorPalette.onSurface
        )
        Text(
            text = topic,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialColorPalette.onSurfaceVariant
        )
    }
}

@Composable
fun Question(
    modifier: Modifier = Modifier,
    questionIndex: Int,
    question: String,
    isLast: Boolean
) {
    Column(
        modifier = modifier.padding(
            start = dimensionResource(id = R.dimen.dimension_16dp),
            end = dimensionResource(id = R.dimen.dimension_16dp),
            top = dimensionResource(id = R.dimen.dimension_8dp),
        ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
    ) {
        Row {
            Text(
                text = "$questionIndex. ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialColorPalette.onSecondaryContainer
            )
            Text(
                text = question,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialColorPalette.onSecondaryContainer
            )
        }
        if (!isLast) Divider()
    }
}

@Preview
@Composable
fun ViewMoreQuestionsScreenPreview(
) {
    ViewMoreQuestionsScreen(
        noteIndex = 2,
        noteId = 1
    )
}
