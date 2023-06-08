package com.twain.interprep.data.ui

import androidx.annotation.StringRes
import com.twain.interprep.R

class AddInterviewData {
    companion object {
        val textInputHorizontalList = listOf(
            Input(
                labelTextId = R.string.hint_label_date,
                bottomTextId = R.string.hint_label_month_format,
                required = true,
                errorTextId = R.string.error_message_form_input_date,
                inputType = TextInputType.DATE
            ),
            Input(
                labelTextId = R.string.hint_label_time,
                bottomTextId = R.string.hint_label_time_format,
                required = true,
                errorTextId = R.string.error_message_form_input_time,
                inputType = TextInputType.TIME
            )
        )

        val textInputVerticalList = listOf(
            Input(
                labelTextId = R.string.hint_label_company,
                required = true,
                errorTextId = R.string.error_message_form_input_company,
                inputType = TextInputType.TEXT
            ),
            Input(
                labelTextId = R.string.hint_label_interview_type,
                inputType = TextInputType.DROPDOWN
            ),
            Input(
                labelTextId = R.string.hint_label_role,
                inputType = TextInputType.DROPDOWN
            ),
            Input(
                labelTextId = R.string.hint_label_round_count,
                inputType = TextInputType.TEXT
            ),
            Input(
                labelTextId = R.string.hint_label_job_post,
                inputType = TextInputType.TEXT
            ),
            Input(
                labelTextId = R.string.hint_label_company_link,
                inputType = TextInputType.TEXT
            ),
            Input(
                labelTextId = R.string.hint_label_interviewer,
                inputType = TextInputType.TEXT
            )
        )
    }
}

data class Input(
    val value: String = "",
    @StringRes val labelTextId: Int,
    @StringRes val bottomTextId: Int? = null,
    val required: Boolean = false,
    @StringRes val errorTextId: Int? = null,
    val inputType: TextInputType = TextInputType.TEXT
)

enum class TextInputType {
    TEXT,
    DATE,
    TIME,
    DROPDOWN
}
