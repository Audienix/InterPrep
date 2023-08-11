package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.data.ui.interviewMockData
import com.twain.interprep.data.ui.notesMockData
import com.twain.interprep.presentation.ui.components.generic.IPCircleIcon
import com.twain.interprep.presentation.ui.components.generic.IPFilledButton
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes

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
                .padding(dimensionResource(id = R.dimen.dimension_16dp)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_8dp))
        ) {
            InterviewDetailForNote(
                Modifier.padding(bottom = dimensionResource(id = R.dimen.dimension_8dp)),
                interview = interview,
                shouldShowMenuOption = true,
                notesEmpty = notes.isEmpty(),
                onDeleteInterview = onDeleteInterviewClick,
                onDeleteNotes = onDeleteNotesForInterviewClick
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = dimensionResource(id = R.dimen.dimension_stroke_width_low),
                color = MaterialColorPalette.outlineVariant
            )
            notes.firstOrNull()?.let { note ->
                Row(
                    modifier = Modifier
                ) {
                    IPCircleIcon(
                        modifier = Modifier
                            .padding(
                                vertical = dimensionResource(id = R.dimen.dimension_16dp)
                            ),
                        text = "1",
                        textStyle = MaterialTheme.typography.titleSmall,
                        textColor = MaterialColorPalette.onSecondaryContainer,
                        size = dimensionResource(id = R.dimen.dimension_icon_size_32),
                        containerColor = MaterialColorPalette.secondaryContainer,
                        borderColor = MaterialColorPalette.surfaceContainerLow,
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_8dp)))
                    Column(
                        modifier = Modifier
                            .padding(
                                vertical = dimensionResource(id = R.dimen.dimension_16dp)
                            )
                            .fillMaxWidth(),
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
            ) {
                IPFilledButton(
                    backgroundColor = MaterialColorPalette.primary,
                    text = stringResource(R.string.button_view_notes),
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
                    text = stringResource(R.string.button_add_notes),
                    textColor = MaterialColorPalette.onPrimary,
                    iconColor = MaterialColorPalette.onPrimary,
                    textStyle = MaterialTheme.typography.labelLarge,
                    onClick = onAddNoteClick,
                    leadingIcon = R.drawable.ic_add_note_24,
                    disabledContentColor = MaterialColorPalette.onPrimaryContainer,
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
