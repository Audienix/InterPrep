package com.twain.interprep.data.ui

import androidx.compose.ui.text.input.ImeAction
import com.twain.interprep.R

object ResourceFormData {
    val resourceForm = listOf(
        TextInputAttributes(
            labelTextId = R.string.hint_label_topic,
            inputType = TextInputType.TEXT,
            imeAction = ImeAction.Next,
            validationType = ValidationType.REQUIRED,
            errorTextId = R.string.error_message_form_input_topic
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_subtopic,
            inputType = TextInputType.TEXT,
            imeAction = ImeAction.Next,
        )
    )

    val linkForm = listOf(
        TextInputAttributes(
            labelTextId = R.string.hint_label_link_description,
            inputType = TextInputType.TEXT,
            imeAction = ImeAction.Next,
            errorTextId = R.string.error_message_form_input_link_description
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_link,
            inputType = TextInputType.TEXT,
            imeAction = ImeAction.Done,
            validationType = ValidationType.REQUIRED,
            errorTextId = R.string.error_message_form_input_link
        )
    )
}
