package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.twain.interprep.R
import com.twain.interprep.data.model.Note
import com.twain.interprep.presentation.ui.components.generic.IPDropdown
import com.twain.interprep.presentation.ui.components.generic.IPDropdownItem
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun ViewNoteCard(
    note: Note,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    index: Int,
    onViewMoreClicked: () -> Unit
) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    ShowDeleteConfirmationDialog(showDeleteDialog, onDeleteClicked)

    val menuItems = getNoteDropdownMenuItems(onEditClicked, showDeleteDialog)

    Card(
        border = BorderStroke(
            dimensionResource(id = R.dimen.dimension_stroke_width_low),
            MaterialColorPalette.surfaceContainerHigh
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialColorPalette.surfaceContainerLow),
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.dimension_16dp),
                vertical = dimensionResource(id = R.dimen.dimension_8dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.dimension_8dp))
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = dimensionResource(id = R.dimen.dimension_16dp))
                        .size(dimensionResource(id = R.dimen.dimension_32dp))
                        .clip(CircleShape)
                        .background(MaterialColorPalette.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = index.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialColorPalette.onSecondaryContainer
                    )
                }
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimension_8dp)))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = dimensionResource(id = R.dimen.dimension_8dp)),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.dimension_4dp)
                    )
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
                    note.questions.subList(0, minOf(2, note.questions.size))
                        .forEachIndexed { index, question ->
                            Text(
                                text = "${index + 1}. $question",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialColorPalette.onSurfaceVariant
                            )
                        }
                }
//                Spacer(modifier = Modifier.weight(1f))
                IPDropdown(menuItems)

            }
            if (note.questions.size > 2) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = dimensionResource(id = R.dimen.dimension_16dp),
                            end = dimensionResource(id = R.dimen.dimension_8dp)
                        )
                        .clickable { onViewMoreClicked() },
                    text = stringResource(id = R.string.label_view_more),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialColorPalette.surfaceTint,
                    textAlign = TextAlign.End
                )
            }
        }

    }
}

@Composable
private fun getNoteDropdownMenuItems(
    onEditClicked: () -> Unit,
    showDeleteDialog: MutableState<Boolean>
): List<IPDropdownItem> {
    val menuItems = mutableListOf<IPDropdownItem>()
    menuItems.add(
        IPDropdownItem(
            stringResource(id = R.string.menuitem_edit_note),
            Icons.Default.Edit,
            onEditClicked
        )
    )
    menuItems.add(
        IPDropdownItem(
            stringResource(id = R.string.menuitem_delete_note),
            Icons.Default.Delete
        ) { showDeleteDialog.value = true })
    return menuItems
}
