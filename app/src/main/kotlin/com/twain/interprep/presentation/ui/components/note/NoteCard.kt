package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.data.ui.interviewMockData
import com.twain.interprep.data.ui.notesMockData
import com.twain.interprep.presentation.ui.components.generic.IPFilledButton
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes
import com.twain.interprep.presentation.ui.theme.TextSecondary

@Composable
fun NoteCard(
    interviewNotePair: Pair<Interview, List<Note>>,
    onViewNoteClick: () -> Unit,
    onAddNoteClick: () -> Unit,
    onDeleteNotesForInterviewClick: () -> Unit,
    onDeleteInterviewClick: () -> Unit
) {
    val (interview, notes) = interviewNotePair
    Card(
        border = BorderStroke(
            dimensionResource(id = R.dimen.dimension_stroke_width_low),
            MaterialColorPalette.surfaceContainerHigh
        ),
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialColorPalette.surfaceContainerLow),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.dimension_16dp),
                vertical = dimensionResource(id = R.dimen.dimension_8dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.dimension_16dp))
        ) {
            Row {
                InterviewDetailForNote(
                    Modifier.padding(bottom = dimensionResource(id = R.dimen.dimension_8dp)),
                    interview = interview,
                    shouldShowDeleteButton = true,
                    notesEmpty = notes.isEmpty(),
                    onDeleteInterview = onDeleteInterviewClick,
                    onDeleteNotes = onDeleteNotesForInterviewClick
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = dimensionResource(id = R.dimen.dimension_stroke_width_low),
                color = MaterialColorPalette.outlineVariant
            )
            notes.firstOrNull()?.let { note ->
                Row(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.dimension_16dp))
                ) {
                    Box(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dimension_32dp))
                            .clip(CircleShape)
                            .background(MaterialColorPalette.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "1",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialColorPalette.onSecondaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_8dp)))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
                    ) {
                        Text(
                            text = note.interviewSegment,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialColorPalette.onSurface
                        )
                        if (note.topic.isNotEmpty()) {
                            Text(
                                text = note.topic,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialColorPalette.onSurface
                            )
                        }
                        Text(
                            text = note.questions.firstOrNull().orEmpty(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialColorPalette.onSurfaceVariant
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.dimension_16dp))
            ) {
                IPFilledButton(
                    backgroundColor = MaterialColorPalette.primary,
                    text = "View Notes",
                    textColor = MaterialColorPalette.onPrimary,
                    enabled = notes.isNotEmpty(),
                    iconColor = MaterialColorPalette.onPrimary,
                    textStyle = MaterialTheme.typography.labelLarge,
                    onClick = onViewNoteClick,
                    leadingIcon = R.drawable.ic_view_note_24,
                    contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dimension_16dp))
                )
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.dimension_8dp)))
                IPFilledButton(
                    backgroundColor = MaterialColorPalette.primary,
                    text = "Add Notes",
                    textColor = MaterialColorPalette.onPrimary,
                    iconColor = MaterialColorPalette.onPrimary,
                    textStyle = MaterialTheme.typography.labelLarge,
                    onClick = onAddNoteClick,
                    leadingIcon = R.drawable.ic_add_note_24,
                    disabledContentColor = TextSecondary,
                    contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dimension_16dp))
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun NoteCardPreview() {
    NoteCard(
        interviewNotePair = interviewMockData to notesMockData,
        onViewNoteClick = {},
        onAddNoteClick = {},
        onDeleteNotesForInterviewClick = {}
    ) {}
}
