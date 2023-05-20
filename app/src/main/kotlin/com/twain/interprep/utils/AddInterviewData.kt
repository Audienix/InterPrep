package com.twain.interprep.utils

import androidx.annotation.StringRes
import com.twain.interprep.R

class AddInterviewData {
    companion object {
        val inputHorizontals = listOf(
            Input(
                labelTextId = R.string.hint_label_date,
                bottomTextId = R.string.hint_label_month_format,
                required = true,
                errorTextId = R.string.error_message_form_input_date
            ),
            Input(
                labelTextId = R.string.hint_label_time,
                bottomTextId = R.string.hint_label_time_format,
                required = true,
                errorTextId = R.string.error_message_form_input_time
            )
        )

        val inputVerticals = listOf(
            Input(
                labelTextId = R.string.hint_label_company, required = true,
                errorTextId = R.string.error_message_form_input_company
            ),
            Input(labelTextId = R.string.hint_label_interview_type),
            Input(labelTextId = R.string.hint_label_role),
            Input(labelTextId = R.string.hint_label_job_post),
            Input(labelTextId = R.string.hint_label_company_link),
            Input(labelTextId = R.string.hint_label_interviewer)
        )
    }
}

data class Input(
    val value: String = "",
    @StringRes val labelTextId: Int,
    @StringRes val bottomTextId: Int? = null,
    val required: Boolean = false,
    @StringRes val errorTextId: Int? = null,
)
