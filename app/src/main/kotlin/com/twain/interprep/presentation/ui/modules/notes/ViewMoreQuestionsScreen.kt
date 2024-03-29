package com.twain.interprep.presentation.ui.modules.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPCircleTextIcon
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun ViewMoreQuestionsScreen(
    viewModel: QuestionViewModel = hiltViewModel(),
    navController: NavHostController,
    noteId: Int,
    noteIndex: Int
) {
    LaunchedEffect(Unit) {
        viewModel.getNote(noteId)
    }
    Scaffold(
        topBar = {
            IPAppBar(
                title = stringResource(id = R.string.appbar_title_question),
                navIcon = {
                    IPIcon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = MaterialColorPalette.onSurfaceVariant
                    ) {
                        navController.popBackStack()
                    }
                }
            )
        },
        containerColor = MaterialColorPalette.surface,
        content = { padding ->
            ShowViewMoreQuestionsContent(
                paddingValues = padding,
                noteIndex = noteIndex,
                interviewSegment = viewModel.note.interviewSegment,
                topic = viewModel.note.topic,
                questions = viewModel.note.questions
            )
        }
    )
}

@Composable
fun ShowViewMoreQuestionsContent(
    paddingValues: PaddingValues,
    noteIndex: Int,
    interviewSegment: String,
    topic: String,
    questions: List<String>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        item {
            NoteDetails(
                noteIndex = noteIndex,
                interviewSegment = interviewSegment,
                topic = topic
            )
        }
        item { HorizontalDivider() }

        itemsIndexed(items = questions) { index, item ->

            Question(
                questionIndex = index + 1,
                question = item,
                isLast = index == questions.lastIndex
            )

            if (index == questions.lastIndex)
                Spacer(
                    modifier = Modifier.padding(
                        bottom = dimensionResource(id = R.dimen.dimension_16dp)
                    )
                )
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
    IPCircleTextIcon(
        modifier = modifier,
        text = index.toString(),
        textStyle = MaterialTheme.typography.titleMedium,
        textColor = MaterialColorPalette.onSecondaryContainer,
        size = dimensionResource(id = R.dimen.dimension_icon_size_48),
        containerColor = MaterialColorPalette.secondaryContainer,
        borderColor = MaterialColorPalette.onSecondaryContainer,
    )
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
            text = topic.ifEmpty {
                stringResource(
                    id = R.string.label_no_text_available
                )
            },
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
        if (!isLast) HorizontalDivider()
    }
}
