package com.twain.interprep.data.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.text.input.KeyboardType
import com.twain.interprep.R
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.data.model.Interview
import com.twain.interprep.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.Locale

object InterviewFormData {
    val interviewFormList = listOf(
        TextInputAttributes(
            labelTextId = R.string.hint_label_date,
            bottomTextId = R.string.hint_label_month_format,
            errorTextId = R.string.error_message_form_input_date,
            inputType = TextInputType.DATE,
            validationType = ValidationType.REQUIRED
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_time,
            bottomTextId = R.string.hint_label_time_format,
            errorTextId = R.string.error_message_form_input_time,
            inputType = TextInputType.TIME,
            validationType = ValidationType.REQUIRED
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_company,
            errorTextId = R.string.error_message_form_input_company,
            inputType = TextInputType.TEXT,
            validationType = ValidationType.REQUIRED
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_meeting_link,
            inputType = TextInputType.TEXT,
            errorTextId = R.string.error_message_invalid_url,
            keyboardType = KeyboardType.Uri,
            validationType = ValidationType.URL,
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_round_count,
            inputType = TextInputType.TEXT,
            keyboardType = KeyboardType.Number,
            errorTextId = R.string.error_message_invalid_number,
            validationType = ValidationType.NUMBER
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_interview_type,
            inputType = TextInputType.DROPDOWN
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_interviewer,
            inputType = TextInputType.TEXT
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_role,
            inputType = TextInputType.DROPDOWN
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_job_post_link,
            errorTextId = R.string.error_message_invalid_url,
            inputType = TextInputType.TEXT,
            keyboardType = KeyboardType.Uri,
            validationType = ValidationType.URL
        ),
        TextInputAttributes(
            labelTextId = R.string.hint_label_company_link,
            inputType = TextInputType.TEXT,
            errorTextId = R.string.error_message_invalid_url,
            keyboardType = KeyboardType.Uri,
            validationType = ValidationType.URL
        )
    )

    fun getInterviewTextLabelList(interview: Interview, context: Context): List<TextLabelData> {
        val date = "".takeIf { interview.date.isEmpty() } ?: SimpleDateFormat(
            StringConstants.DT_FORMAT_DD_MMMM_YYYY,
            Locale.getDefault()
        ).format(DateUtils.convertDateStringToDate(interview.date))
        return listOf(
            TextLabelData(
                R.string.hint_label_meeting_link, interview.meetingLink
            ),
            TextLabelData(
                R.string.hint_label_date, date
            ),
            TextLabelData(
                R.string.hint_label_time, DateUtils.getDisplayedTime(context, interview.time)
            ),
            TextLabelData(
                R.string.hint_label_company, interview.company
            ),

            TextLabelData(
                R.string.hint_label_round_count, "${interview.roundNum} - ${interview.interviewType}"
            ),
            TextLabelData(
                R.string.hint_label_interviewer, interview.interviewer
            )
        )
    }

    fun getCompanyTextLabelList(interview: Interview): List<TextLabelData> {
        return listOf(
            TextLabelData(
                R.string.hint_label_role, interview.role,
            ),
            TextLabelData(
                R.string.hint_label_job_post_link, interview.jobPostLink
            ),
            TextLabelData(
                R.string.hint_label_company_link, interview.companyLink
            )
        )
    }
}

data class TextInputAttributes(
    @StringRes val labelTextId: Int,
    @StringRes val bottomTextId: Int? = null,
    @StringRes val errorTextId: Int? = null,
    val inputType: TextInputType = TextInputType.TEXT,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val validationType: ValidationType = ValidationType.NONE
)

enum class TextInputType {
    TEXT,
    DATE,
    TIME,
    DROPDOWN
}

enum class ValidationType {
    NONE,
    REQUIRED,
    NUMBER,
    URL
}

data class TextLabelData(
    @StringRes val labelTextId: Int,
    val labelValue: String = ""
)
