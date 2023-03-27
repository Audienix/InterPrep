package com.twain.interprep.presentation.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
//import com.twain.interprep.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
//    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
//    bottomText: String = "",
    required: Boolean = false,
//    isError: Boolean,
//    errorMessage: String = "",
    // options: List<String>? = null
) {
    val label = if (required) "$labelText *" else labelText

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
//        modifier = modifier,
        singleLine = true,
        label = { Text(text = label) },
//        isError = isError,
//        supportingText = {
//            if (isError) {
//                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
//            } else {
//                Text(text = bottomText)
//            }
//        },
//        trailingIcon = {
//            if (isError)
//                Icon(painter = painterResource(id = R.drawable.error_icon),
//                     contentDescription = "Error")
//        },
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun FormInputPreview() {
    var text by remember { mutableStateOf("") }

    FormInput(
//        modifier = Modifier,
        value = text,
        onValueChange = { text = it },
        labelText = "Company",
        required = true,
//        isError = false,
//        errorMessage = stringResource(id = R.string.form_input_error_message)
    )
}