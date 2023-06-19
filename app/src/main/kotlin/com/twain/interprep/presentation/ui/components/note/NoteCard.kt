package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.Note
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun NoteCard(
    interviewNotePair: Pair<Interview, List<Note>>,
    onDeleteNoteClick: (Note) -> Unit,
    onViewNoteClick: () -> Unit,
    onAddNoteClick: () -> Unit
){

    val (interview, notes) = interviewNotePair

    ElevatedCard(
        shape = Shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(id = R.dimen.dimension_4dp)),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dimension_8dp))
            .fillMaxWidth()
    ) {
        Column {
            Row() {
                InterviewDetailForNote(interview = interview)
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "...")
                }
            }
            if (notes.isNotEmpty()){
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
            Row() {
                Button(onClick = onViewNoteClick) {
                    Text(text = "View Notes")
                }
                Button(onClick = onAddNoteClick) {
                    Text(text = "Add notes")
                }
            }
        }
    }
}