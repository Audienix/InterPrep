package com.twain.interprep.data.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.text.input.KeyboardType
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.utils.DateUtils

object InterviewFormData {
    val textInputHorizontalList = listOf(
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

    val textInputVerticalList = listOf(
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
            inputType = TextInputType.TEXT,
            keyboardType = KeyboardType.Number
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_job_post,
            inputType = TextInputType.TEXT,
            keyboardType = KeyboardType.Uri
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_company_link,
            inputType = TextInputType.TEXT,
            keyboardType = KeyboardType.Uri
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_interviewer,
            inputType = TextInputType.TEXT
        )
    )

    fun getTextLabelList(interview: Interview, context: Context): List<TextLabelData> {
        return listOf(
            TextLabelData(
                R.string.hint_label_time, DateUtils.getDisplayedTime(context, interview.time)
            ),
            TextLabelData(
                R.string.hint_label_company, interview.company
            ),
            TextLabelData(
                R.string.hint_label_interview_type, interview.interviewType
            ),
            TextLabelData(
                R.string.hint_label_role, interview.role,
            ),
            TextLabelData(
                R.string.hint_label_round_count, interview.roundNum
            ),
            TextLabelData(
                R.string.hint_label_job_post, interview.jobPostLink
            ),
            TextLabelData(
                R.string.hint_label_company_link, interview.companyLink
            ),
            TextLabelData(
                R.string.hint_label_interviewer, interview.interviewer
            )
        )
    }
}

data class TextInputAttributes(
    @StringRes val labelTextId: Int,
    @StringRes val bottomTextId: Int? = null,
    val required: Boolean = false,
    @StringRes val errorTextId: Int? = null,
    val inputType: TextInputType = TextInputType.TEXT,
    val keyboardType: KeyboardType = KeyboardType.Text
)

enum class TextInputType {
    TEXT,
    DATE,
    TIME,
    DROPDOWN
}

data class TextLabelData(
    @StringRes val labelTextId: Int,
    val labelValue: String = ""
)
