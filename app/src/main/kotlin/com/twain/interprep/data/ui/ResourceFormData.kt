package com.twain.interprep.data.ui

import com.twain.interprep.R

object ResourceFormData {
    val resourceForm = listOf(
        TextInputAttributes(
            labelTextId = R.string.hint_label_topic,
            inputType = TextInputType.TEXT,
            validationType = ValidationType.REQUIRED,
            errorTextId = R.string.error_message_form_input_topic
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_subtopic,
            inputType = TextInputType.TEXT
        )
    )

    val linkForm = listOf(
        TextInputAttributes(
            labelTextId = R.string.hint_label_link_description,
            inputType = TextInputType.TEXT,
            errorTextId = R.string.error_message_form_input_link_description
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_link,
            inputType = TextInputType.TEXT,
            validationType = ValidationType.REQUIRED,
            errorTextId = R.string.error_message_form_input_link
        )
    )
}
