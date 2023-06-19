package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.twain.interprep.R
import com.twain.interprep.data.model.Note
import com.twain.interprep.data.ui.InterviewFormData
import com.twain.interprep.presentation.ui.components.interview.IPTextInput
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun AddNoteCard(
    note: Note,
    getNoteField: (Int) -> String,
    updateNoteField: ( Int, String) -> Unit,
    updateQuestion: (Int, String) -> Unit,
    addQuestion: () -> Unit
) {
    ElevatedCard(
        shape = Shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(id = R.dimen.dimension_4dp)),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dimension_8dp))
            .fillMaxWidth()
    ) {
        Column() {
            InterviewFormData.noteFormList.map { input ->
                IPTextInput(
                    modifier = Modifier.fillMaxWidth(),
                    inputText = getNoteField(input.labelTextId),
                    textInputAttributes = input,
                    onTextUpdate = {
                        updateNoteField(input.labelTextId, it)
                    }
                )
            }
            note.questions.forEachIndexed { index, question ->
                IPTextInput(
                    inputText = question,
                    textInputAttributes = InterviewFormData.getQuestion(),
                    onTextUpdate = {
                        updateQuestion(index, it)
                    }
                )
            }
            Button(onClick = { addQuestion() }) {
                Text(text = "Add Question")
            }
        }
    }
}