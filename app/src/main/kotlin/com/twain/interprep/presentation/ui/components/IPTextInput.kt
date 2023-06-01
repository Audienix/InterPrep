package com.twain.interprep.presentation.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import com.twain.interprep.R
import com.twain.interprep.data.ui.Input
import com.twain.interprep.data.ui.TextInputType
import com.twain.interprep.utils.validateRequiredField

@Composable
fun IPTextInput(
    modifier: Modifier = Modifier,
    input: Input,
) {
    val labelText = stringResource(id = input.labelTextId)
    val bottomText = input.bottomTextId?.let { stringResource(id = it) } ?: ""
    val errorText = input.errorTextId?.let { stringResource(id = it) } ?: ""
    val label = if (input.required) "$labelText *" else labelText

    var text by remember { mutableStateOf(input.value) }
    var isError by remember { mutableStateOf(false) }
    val source = remember { MutableInteractionSource() }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    OutlinedTextField(
        modifier = modifier.onGloballyPositioned { coordinates ->
            //This value is used to assign to the DropDown the same width
            textFieldSize = coordinates.size.toSize()
        },
        value = text,
        onValueChange = {
            text = it
            if (input.required) isError = validateRequiredField(text)
        },
        interactionSource = source,
        singleLine = true,
        label = { Text(text = label) },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = errorText,
                    color = MaterialTheme.colorScheme.error,
                )
            } else if (bottomText != "") {
                Text(text = bottomText)
            }
        },
        trailingIcon = {
            if (isError) {
                Icon(
                    painter = painterResource(id = R.drawable.error_icon),
                    contentDescription = "Error"
                )
            } else if (text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        text = ""
                        if (input.required) isError = validateRequiredField(text)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel_icon),
                        contentDescription = "Cancel"
                    )
                }
            }
        },
    )
    HandleComponentInteraction(source, input, modifier, text, textFieldSize) {
        text = it
        if (input.required) isError = validateRequiredField(text)
    }
}

@Composable
private fun HandleComponentInteraction(
    source: MutableInteractionSource,
    input: Input,
    modifier: Modifier,
    fieldText: String,
    textFieldSize: Size,
    onTextUpdate: (text: String) -> Unit
) {
    val pressedState = source.interactions.collectAsState(
        initial = PressInteraction.Cancel(PressInteraction.Press(Offset.Zero))
    )
    if (pressedState.value is PressInteraction.Release) {
        when (input.inputType) {
            TextInputType.DATE -> IPDatePicker(
                selectedDateValue = fieldText,
                onDatePickerDismiss = { onTextUpdate(it) },
            )

            TextInputType.DROPDOWN -> {
                var dropdownOptions = emptyList<String>()
                if (input.labelTextId == R.string.hint_label_interview_type)
                    dropdownOptions =
                        listOf("Recruiter", "Hiring Manager", "Technical", "Behavioral")
                else if (input.labelTextId == R.string.hint_label_role)
                    dropdownOptions = listOf(
                        "Software Engineer",
                        "Sr. Software Engineer",
                        "Staff Engineer",
                        "Engineering Manager"
                    )

                IPDropdownMenu(
                    modifier = modifier.fillMaxWidth(),
                    options = dropdownOptions,
                    textFieldSize = textFieldSize,
                    onDropdownDismiss = { onTextUpdate(it) }
                )
            }

            else -> {}
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun TextFormInputPreview() {
    val options = listOf("Recruiter", "Hiring Manager", "Technical", "Behavioral")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimension_16dp)),
    ) {
        IPTextInput(
            modifier = Modifier.fillMaxWidth(),
            input = Input(
                labelTextId = R.string.hint_label_company,
                required = true,
                errorTextId = R.string.error_message_form_input
            )
        )
        IPTextInput(
            modifier = Modifier.fillMaxWidth(),
            input = Input(
                labelTextId = R.string.hint_label_date,
                bottomTextId = R.string.hint_label_month_format,
                required = false
            )
        )
        IPDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            options = options,
            textFieldSize = Size.Zero,
            onDropdownDismiss = {}
        )
    }
}
