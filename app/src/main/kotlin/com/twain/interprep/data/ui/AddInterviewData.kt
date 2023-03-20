package com.twain.interprep.data.ui

import androidx.annotation.StringRes
import com.twain.interprep.R

class AddInterviewData {
    companion object {
        val textTextInputHorizontalListAttributes = listOf(
            TextInputAttributes(
                labelTextId = R.string.hint_label_date,
                bottomTextId = R.string.hint_label_month_format,
                required = true,
                errorTextId = R.string.error_message_form_input_date,
                inputType = TextInputType.DATE
            ),
            TextInputAttributes(
                labelTextId = R.string.hint_label_time,
                bottomTextId = R.string.hint_label_time_format,
                required = true,
                errorTextId = R.string.error_message_form_input_time,
                inputType = TextInputType.TIME
            )
        )

        val textTextInputVerticalListAttributes = listOf(
            TextInputAttributes(
                labelTextId = R.string.hint_label_company,
                required = true,
                errorTextId = R.string.error_message_form_input_company,
                inputType = TextInputType.TEXT
            ),
            TextInputAttributes(
                labelTextId = R.string.hint_label_interview_type,
                inputType = TextInputType.DROPDOWN
            ),
            TextInputAttributes(
                labelTextId = R.string.hint_label_role,
                inputType = TextInputType.DROPDOWN
            ),
            TextInputAttributes(
                labelTextId = R.string.hint_label_round_count,
                inputType = TextInputType.TEXT
            ),
            TextInputAttributes(
                labelTextId = R.string.hint_label_job_post,
                inputType = TextInputType.TEXT
            ),
            TextInputAttributes(
                labelTextId = R.string.hint_label_company_link,
                inputType = TextInputType.TEXT
            ),
            TextInputAttributes(
                labelTextId = R.string.hint_label_interviewer,
                inputType = TextInputType.TEXT
            )
        )
    }
}

data class TextInputAttributes(
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
