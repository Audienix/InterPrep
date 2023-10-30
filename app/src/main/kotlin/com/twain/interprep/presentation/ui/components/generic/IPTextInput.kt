package com.twain.interprep.presentation.ui.components.generic

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import com.twain.interprep.R
import com.twain.interprep.data.ui.InterviewFormData.getInterviewRoleList
import com.twain.interprep.data.ui.InterviewFormData.getInterviewTypeList
import com.twain.interprep.data.ui.TextInputAttributes
import com.twain.interprep.data.ui.TextInputType
import com.twain.interprep.data.ui.ValidationType
import com.twain.interprep.presentation.ui.components.interview.IPDatePicker
import com.twain.interprep.presentation.ui.components.interview.IPTimePicker
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.utils.DateUtils
import com.twain.interprep.utils.isValidTextInput

@Composable
fun IPTextInput(
    modifier: Modifier = Modifier,
    inputText: String,
    isBackPressed: Boolean = false,
    textInputAttributes: TextInputAttributes,
    onTextUpdate: (text: String) -> Unit
) {
    val labelText = stringResource(id = textInputAttributes.labelTextId)
    val bottomText = textInputAttributes.bottomTextId?.let { stringResource(id = it) } ?: ""
    val errorText = textInputAttributes.errorTextId?.let { stringResource(id = it) } ?: ""
    val label =
        if (textInputAttributes.validationType == ValidationType.REQUIRED) "$labelText *" else labelText

    val context = LocalContext.current

    var isError by remember { mutableStateOf(false) }
    val source = remember { MutableInteractionSource() }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    var hasFocus by remember { mutableStateOf(false) }

    val displayedText = when (textInputAttributes.labelTextId) {
        R.string.hint_label_time -> DateUtils.getDisplayedTime(context, inputText)
        else -> inputText
    }

    LaunchedEffect(isBackPressed) {
        if (isBackPressed) isError = !isValidTextInput(true, inputText, textInputAttributes)
    }

    OutlinedTextField(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                //This value is used to assign to the DropDown the same width
                textFieldSize = coordinates.size.toSize()
            }
            .onFocusChanged { hasFocus = it.hasFocus },
        value = displayedText,
        onValueChange = {
            onTextUpdate(it)
            if (textInputAttributes.validationType != ValidationType.NONE)
                isError = !isValidTextInput(false, it, textInputAttributes)
        },
        interactionSource = source,
        singleLine = textInputAttributes.singleLine,
        label = { Text(text = label) },
        isError = isError,
        keyboardActions = textInputAttributes.keyboardActions,
        keyboardOptions = KeyboardOptions(
            keyboardType = textInputAttributes.keyboardType,
            imeAction = textInputAttributes.imeAction
        ),
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
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = MaterialColorPalette.error
                )
            } else if (inputText.isNotEmpty() && hasFocus) {
                IconButton(
                    onClick = {
                        onTextUpdate("")
                        isError = !isValidTextInput(false, "", textInputAttributes)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel"
                    )
                }
            }

        },
        textStyle = LocalTextStyle.current.copy(color = MaterialColorPalette.onSurface),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialColorPalette.primary,
            focusedLabelColor = MaterialColorPalette.primary,
            unfocusedPlaceholderColor = MaterialColorPalette.onSurfaceVariant,
            focusedPlaceholderColor = MaterialColorPalette.onSurfaceVariant,
            focusedTrailingIconColor = MaterialColorPalette.onSurfaceVariant,
            unfocusedLabelColor = MaterialColorPalette.onSurfaceVariant
        )
    )
    HandleComponentInteraction(source, textInputAttributes, inputText, textFieldSize) {
        isError = false
        onTextUpdate(it)
    }
}

@Composable
private fun HandleComponentInteraction(
    source: MutableInteractionSource,
    textInputAttributes: TextInputAttributes,
    fieldText: String,
    textFieldSize: Size,
    onTextUpdate: (text: String) -> Unit
) {
    val pressedState = source.interactions.collectAsState(
        initial = PressInteraction.Cancel(PressInteraction.Press(Offset.Zero))
    )
    if (pressedState.value is PressInteraction.Release) {
        when (textInputAttributes.inputType) {
            TextInputType.DATE -> IPDatePicker(
                selectedDateValue = fieldText,
                onDatePickerDismiss = { onTextUpdate(it) },
            )

            TextInputType.TIME -> IPTimePicker(
                selectedTimeValue = fieldText,
                onTimePickerDismiss = { onTextUpdate(it) }
            )

            TextInputType.DROPDOWN -> {
                var dropdownOptions = emptyList<String>()
                when (textInputAttributes.labelTextId) {
                    R.string.hint_label_interview_type -> dropdownOptions = getInterviewTypeList()
                    R.string.hint_label_role -> dropdownOptions = getInterviewRoleList()
                    R.string.label_setting_language -> dropdownOptions =
                        stringArrayResource(id = R.array.language_option).toCollection(ArrayList())
                }

                IPDropdownMenu(
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
            textInputAttributes = TextInputAttributes(
                labelTextId = R.string.hint_label_company,
                errorTextId = R.string.error_message_form_input
            ),
            inputText = "",
            onTextUpdate = {}
        )
        IPTextInput(
            modifier = Modifier.fillMaxWidth(),
            textInputAttributes = TextInputAttributes(
                labelTextId = R.string.hint_label_date,
                bottomTextId = R.string.hint_label_month_format,
            ),
            inputText = "",
            onTextUpdate = {}
        )
        IPTextInput(
            modifier = Modifier.fillMaxWidth(),
            textInputAttributes = TextInputAttributes(
                labelTextId = R.string.hint_label_date,
                bottomTextId = R.string.hint_label_month_format,
            ),
            inputText = "",
            onTextUpdate = {}
        )
        IPTextInput(
            modifier = Modifier.fillMaxWidth(),
            textInputAttributes = TextInputAttributes(
                labelTextId = R.string.hint_label_time,
                bottomTextId = R.string.hint_label_time_format
            ),
            onTextUpdate = {},
            inputText = ""
        )
        IPDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            options = options,
            textFieldSize = Size.Zero,
            onDropdownDismiss = {}
        )
    }
}
