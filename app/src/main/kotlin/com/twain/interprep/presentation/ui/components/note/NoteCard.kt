package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.presentation.ui.components.generic.IPFilledButton
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple
import com.twain.interprep.presentation.ui.theme.Shapes

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
                vertical = dimensionResource(id = R.dimen.dimension_24dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = dimensionResource(id = R.dimen.dimension_16dp)
                )
        ) {
            Row() {
                InterviewDetailForNote(interview = interview)
            }
            if (notes.isNotEmpty()) {
                Divider()
                notes.forEachIndexed { index, note ->
                    Row(modifier = Modifier.clickable { onDeleteNoteClick(note) }) {
                        Text(text = index.toString())
                        Column() {
                            Text(text = note.interviewSegment)
                            Text(text = note.topic)
                            note.questions.forEach {
                                Text(text = it)
                            }
                        }
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.dimension_24dp))
            ) {
                IPFilledButton(
                    backgroundColor = BackgroundDarkPurple,
                    text = "View Notes",
                    textColor = BackgroundLightPurple,
                    enabled = false,
                    iconColor = BackgroundLightPurple,
                    textStyle = MaterialTheme.typography.labelLarge,
                    onClick = onViewNoteClick,
                    leadingIcon = R.drawable.filled_reorder,
                    contentPadding =
                    PaddingValues(horizontal = dimensionResource(id = R.dimen.dimension_16dp))
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
                    contentPadding =
                    PaddingValues(horizontal = dimensionResource(id = R.dimen.dimension_16dp))
                )
            }
        }
    }
}
