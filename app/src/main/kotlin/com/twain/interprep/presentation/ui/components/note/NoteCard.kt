package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
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
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple
import com.twain.interprep.presentation.ui.theme.Purple100
import com.twain.interprep.presentation.ui.theme.Shapes
import com.twain.interprep.presentation.ui.theme.TextSecondary

@Composable
fun NoteCard(
    interviewNotePair: Pair<Interview, List<Note>>,
    onDeleteNoteClick: (Note) -> Unit,
    onViewNoteClick: () -> Unit,
    onAddNoteClick: () -> Unit
) {
    val (interview, notes) = interviewNotePair
    ElevatedCard(
        shape = Shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(id = R.dimen.dimension_4dp)),
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
                    Modifier.padding(bottom = dimensionResource(id = R.dimen.dimension_16dp)),
                    interview = interview
                )
            }
            if (notes.isNotEmpty()) {
                Divider()
                notes.forEachIndexed { index, note ->
                    Row(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.dimension_16dp))
                            .clickable { onDeleteNoteClick(note) }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.dimension_32dp))
                                .clip(CircleShape)
                                .background(Purple100),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = index.toString(),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_8dp)))
                        Column(
                            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_4dp))
                        ) {
                            Text(
                                text = note.interviewSegment,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            if (note.topic.isNotEmpty()) {
                                Text(
                                    text = note.topic,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            note.questions.forEach {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp),
                        vertical = dimensionResource(id = R.dimen.dimension_24dp))
            ) {
                IPFilledButton(
                    backgroundColor = BackgroundDarkPurple,
                    text = "View Notes",
                    textColor = BackgroundLightPurple,
                    enabled = false,
                    iconColor = TextSecondary,
                    textStyle = MaterialTheme.typography.labelLarge,
                    onClick = onViewNoteClick,
                    leadingIcon = R.drawable.filled_reorder,
                    disabledContentColor = TextSecondary,
                    contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dimension_16dp))
                )
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.dimension_8dp)))
                IPFilledButton(
                    backgroundColor = BackgroundDarkPurple,
                    text = "Add Notes",
                    textColor = BackgroundLightPurple,
                    iconColor = BackgroundLightPurple,
                    textStyle = MaterialTheme.typography.labelLarge,
                    onClick = onAddNoteClick,
                    leadingIcon = R.drawable.filled_post_add,
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
        onDeleteNoteClick = {},
        onViewNoteClick = {},
        onAddNoteClick = {}
    )
}
