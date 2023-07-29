package com.twain.interprep.data.ui

import com.twain.interprep.R

object NoteFormData {
    val noteFormList = listOf(
        TextInputAttributes(
            labelTextId = R.string.hint_label_interview_segment,
            inputType = TextInputType.TEXT,
            required = true,
            errorTextId = R.string.error_message_form_input_interview_segment
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_topic,
            inputType = TextInputType.TEXT
        )
    )

    fun getQuestion() = TextInputAttributes(
        labelTextId = R.string.hint_label_question,
        inputType = TextInputType.TEXT,
        required = true,
        errorTextId = R.string.error_message_form_input_question
    )
}
