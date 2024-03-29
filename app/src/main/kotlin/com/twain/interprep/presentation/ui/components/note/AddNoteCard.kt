package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.twain.interprep.R
import com.twain.interprep.data.model.Note
import com.twain.interprep.data.ui.NoteFormData
import com.twain.interprep.presentation.ui.components.generic.IPOutlinedButton
import com.twain.interprep.presentation.ui.components.generic.IPTextInput
import com.twain.interprep.presentation.ui.components.generic.IPTextInputDeletable
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun AddNoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    getNoteField: (Int) -> String,
    updateNoteField: (Int, String) -> Unit,
    updateQuestion: (Int, String) -> Unit,
    addQuestion: () -> Unit,
    deleteNote: () -> Unit,
    deleteQuestion: (Int) -> Unit,
    shouldValidate: Boolean
) {
    val showDeleteAlert = remember { mutableStateOf(false) }
    ShowDeleteConfirmationDialog(showDeleteAlert, deleteNote)
    Card(
        border = BorderStroke(
            dimensionResource(id = R.dimen.dimension_stroke_width_low),
            MaterialColorPalette.surfaceContainerHigh
        ),
        shape = Shapes.medium,
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialColorPalette.surfaceContainerLow),
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.dimension_8dp))
        ) {
            NoteFormData.noteFormList.map { input ->
                IPTextInput(
                    modifier = Modifier.fillMaxWidth(),
                    inputText = getNoteField(input.labelTextId),
                    textInputAttributes = input,
                    onTextUpdate = {
                        updateNoteField(input.labelTextId, it)
                    },
                    isBackPressed = shouldValidate
                )
            }
            val showDeleteQuestion = (note.questions.size > 1)
            note.questions.forEachIndexed { index, question ->
                IPTextInputDeletable(
                    modifier = Modifier.fillMaxWidth(),
                    inputText = question,
                    textInputAttributes = NoteFormData.getQuestion(),
                    onTextUpdate = {
                        updateQuestion(index, it)
                    },
                    isBackPressed = shouldValidate,
                    onDeleteClicked = {
                        deleteQuestion(index)
                    },
                    showDeleteIcon = showDeleteQuestion
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(id = R.dimen.dimension_4dp)
                    )
            ) {
                IPOutlinedButton(
                    backgroundColor = Color.Transparent,
                    textColor = MaterialColorPalette.onPrimaryContainer,
                    text = stringResource(id = R.string.button_add_question),
                    iconColor = MaterialColorPalette.onPrimaryContainer,
                    leadingIcon = R.drawable.ic_outline_add_circle_24,
                    onClick = { addQuestion() },
                    borderColor = MaterialColorPalette.onPrimaryContainer,
                    textStyle = MaterialTheme.typography.labelLarge,
                    contentPadding = PaddingValues(
                        horizontal = dimensionResource(id = R.dimen.dimension_8dp)
                    )
                )
            }
        }
    }
}

