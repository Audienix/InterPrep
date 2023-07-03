package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import com.twain.interprep.R
import com.twain.interprep.data.model.Note
import com.twain.interprep.presentation.ui.components.generic.DeleteIcon
import com.twain.interprep.presentation.ui.components.generic.EditIcon
import com.twain.interprep.presentation.ui.theme.Purple100
import com.twain.interprep.presentation.ui.theme.Purple500
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun ViewNoteCard(
    note: Note,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    index: Int
) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    ShowDeleteConfirmationDialog(showDeleteDialog, onDeleteClicked)

    Card(
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.dimension_16dp),
                vertical = dimensionResource(id = R.dimen.dimension_8dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp))
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.dimension_16dp))
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
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = dimensionResource(id = R.dimen.dimension_16dp)),
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.dimension_4dp)
                )
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
                note.questions.subList(0, minOf(2, note.questions.size))
                    .forEachIndexed { index, question ->
                        Text(
                            text = "${index + 1}. $question",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column() {
                EditIcon(onEditIconClick = onEditClicked)
                DeleteIcon(Purple500, onDeleteIconClick = {
                    showDeleteDialog.value = true
                })
            }

        }

    }
}