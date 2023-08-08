package com.twain.interprep.presentation.ui.modules.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.presentation.ui.components.generic.IPAppBar
import com.twain.interprep.presentation.ui.components.generic.IPIcon
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette

@Composable
fun ViewMoreQuestionsScreen(
    noteIndex: Int,
    interviewSegment: String,
    topic: String,
    questions: List<String>
) {
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
                interviewSegment = interviewSegment,
                topic = topic
            )
        }

        itemsIndexed(items = questions) {index, item ->

            Question(
                questionIndex = index + 1,
                question = item,
                isLast = index == questions.lastIndex
            )

            if (index == questions.lastIndex)
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
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialColorPalette.secondaryContainer)
            .border(
                width = dimensionResource(id = R.dimen.dimension_stroke_width_low),
                color = MaterialColorPalette.onSecondaryContainer)
            .size(dimensionResource(id = R.dimen.dimension_icon_size_large)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = index.toString(),
            style = MaterialTheme.typography.titleLarge
        )
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
        interviewSegment = "Technical Question",
        topic = "Kotlin Basics",
        questions = listOf(
            "Difference between sealed & enum class",
            "Types of Dispatcher, Error handling in coroutine",
            "Differentiate between Kotlin’s Variable Declaration Methods.",
            "What is the Function of the Elvis Operator?",
            "What are Kotlin Sealed Classes?",
            "Explain why the Following Code Fails to compile.\n" +
                    "class A{ }\n" +
                    "class B : A(){ }"
        )
    )
}
