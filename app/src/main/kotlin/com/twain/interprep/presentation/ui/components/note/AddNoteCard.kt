package com.twain.interprep.presentation.ui.components.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import com.twain.interprep.data.ui.InterviewFormData
import com.twain.interprep.data.ui.TextInputAttributes
import com.twain.interprep.presentation.ui.components.generic.IPOutlinedButton
import com.twain.interprep.presentation.ui.components.generic.IPTextInput
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundPalePurple
import com.twain.interprep.presentation.ui.theme.Shapes

@Composable
fun AddNoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    getNoteField: (Int) -> String,
    updateNoteField: ( Int, String) -> Unit,
    updateQuestion: (Int, String) -> Unit,
    addQuestion: () -> Unit,
    shouldValidate: Boolean
) {
    ElevatedCard(
        shape = Shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(id = R.dimen.dimension_4dp)),
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(containerColor = BackgroundPalePurple)
    ) {
        Column( modifier = Modifier
            .padding(dimensionResource(id = R.dimen.dimension_8dp))) {
            InterviewFormData.noteFormList.map { input ->
                IPTextInput(
                    modifier = Modifier.fillMaxWidth(),
                    inputText = getNoteField(input.labelTextId),
                    textInputAttributes = input,
                    onTextUpdate = {
                        updateNoteField(input.labelTextId, it)
                    },
                    shouldValidate = shouldValidate
                )
            }
            note.questions.forEachIndexed { index, question ->
                IPTextInput(
                    modifier = Modifier.fillMaxWidth(),
                    inputText = question,
                    textInputAttributes = InterviewFormData.getQuestion(),
                    onTextUpdate = {
                        updateQuestion(index, it)
                    },
                    shouldValidate = shouldValidate
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( top = dimensionResource(id = R.dimen.dimension_4dp)
                    )
            ) {
                IPOutlinedButton(
                    backgroundColor = BackgroundPalePurple,
                    textColor = Color.Black,
                    text = stringResource(id = R.string.add_note_question),
                    iconColor = BackgroundDarkPurple,
                    leadingIcon = R.drawable.outline_add_circle,
                    onClick = { addQuestion() },
                    borderColor = BackgroundDarkPurple,
                    textStyle = MaterialTheme.typography.titleMedium,
                    contentPadding = PaddingValues(
                        horizontal = dimensionResource(id = R.dimen.dimension_16dp)
                    )
                )
            }
        }
    }
}
