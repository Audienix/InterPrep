package com.twain.interprep.data.ui

import androidx.annotation.StringRes
import com.twain.interprep.R
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.data.model.Interview
import java.text.SimpleDateFormat
import java.util.Locale

class InterviewFormData {
    companion object {
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

        fun getTextLabelList(interview: Interview): List<TextLabelData> {
            return listOf(
                TextLabelData(
                    R.string.hint_label_time, SimpleDateFormat(
                        StringConstants.DT_FORMAT_DAY_HOUR_MIN,
                        Locale.getDefault()
                    ).format(interview.date)
                ),
                TextLabelData(
                    R.string.hint_label_company, interview.company
                ),
                TextLabelData(
                    R.string.hint_label_interview_type, interview.interviewType.toString()
                ),
                TextLabelData(
                    R.string.hint_label_role, interview.role.toString(),
                ),
                TextLabelData(
                    R.string.hint_label_round_count, interview.roundNum.toString()
                ),
                TextLabelData(
                    R.string.hint_label_job_post, interview.jobPostLink.toString()
                ),
                TextLabelData(
                    R.string.hint_label_company_link, interview.companyLink.toString()
                ),
                TextLabelData(
                    R.string.hint_label_interviewer, interview.interviewer.toString()
                )
            )
        }
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

data class TextLabelData(
    @StringRes val labelTextId: Int,
    val labelValue: String = ""
)
