package com.twain.interprep.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    modifier: Modifier,
    value: String,
    labelText: String,
    bottomText: String = "",
    required: Boolean = false,
    errorMessage: String = "",
) {
    val label = if (required) "$labelText *" else labelText
    var text by remember { mutableStateOf(value) }
    var isError by remember { mutableStateOf(false) }

    fun validate(text: String) {
        isError = (required && text == "")
    }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            validate(text)
        },
        modifier = modifier,
        singleLine = true,
        label = { Text(text = label) },
        isError = isError,
        keyboardActions = KeyboardActions { validate(text) },
        supportingText = {
            if (isError) {
                Text(
                    modifier = modifier.padding(
                        bottom = dimensionResource(id = R.dimen.dimension_8dp)),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error)
            } else if (bottomText != "") {
                Text(
                    modifier = modifier.padding(
                        bottom = dimensionResource(id = R.dimen.dimension_8dp)),
                    text = bottomText)
            }
        },
        trailingIcon = {
            if (isError)
                Icon(painter = painterResource(id = R.drawable.error_icon),
                     contentDescription = "Error")
        },
    )
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun FormInputPreview() {
    var text by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }

    val options = listOf<String>("Recruiter", "Hiring Manager", "Technical", "Behavioral")

    Column() {
        FormInput(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            labelText = "Company",
            required = true,
            errorMessage = stringResource(id = R.string.form_input_error_message)
        )
        FormInput(
            modifier = Modifier.fillMaxWidth(),
            value = text2,
            labelText = "Name",
            required = false
        )
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            options = options,
            labelText = "Interview Type"
        )
    }
}