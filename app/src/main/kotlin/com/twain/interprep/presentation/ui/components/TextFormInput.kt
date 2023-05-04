package com.twain.interprep.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twain.interprep.R
import com.twain.interprep.utils.validateRequiredField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFormInput(
    modifier: Modifier = Modifier,
    value: String = "",
    labelText: String,
    bottomText: String = "",
    required: Boolean = false,
    errorText: String = "",
) {
    val label = if (required) "$labelText *" else labelText
    var text by remember { mutableStateOf(value) }
    var isError by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            if (required) isError = validateRequiredField(text)
        },
        modifier = modifier,
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
                    onClick = { /* TODO: implement onClick for clearing text */ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel_icon),
                        contentDescription = "Cancel"
                    )
                }
            }
        },
    )
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
        TextFormInput(
            modifier = Modifier.fillMaxWidth(),
            labelText = "Company",
            required = true,
            errorText = stringResource(id = R.string.error_message_form_input),
        )
        TextFormInput(
            modifier = Modifier.fillMaxWidth(),
            value = "Sarah",
            labelText = "Name",
            required = false
        )
        TextFormInput(
            modifier = Modifier.fillMaxWidth(),
            labelText = "Date",
            bottomText = "MM/YY",
            required = false
        )
        DropdownMenuInput(
            modifier = Modifier.fillMaxWidth(),
            options = options,
            labelText = "Interview Type"
        )
    }
}
