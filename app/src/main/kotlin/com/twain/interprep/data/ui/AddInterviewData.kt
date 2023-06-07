package com.twain.interprep.data.ui

import androidx.annotation.StringRes
import com.twain.interprep.R
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddInterviewData {
    companion object {
        val textInputHorizontalList = listOf(
            Input(
                interviewLabel = InterviewLabel.DATE,
                bottomTextId = R.string.hint_label_month_format,
                required = true,
                errorTextId = R.string.error_message_form_input_date,
                inputType = TextInputType.DATE
            ),
            Input(
                interviewLabel = InterviewLabel.TIME,
                bottomTextId = R.string.hint_label_time_format,
                required = true,
                errorTextId = R.string.error_message_form_input_time,
                inputType = TextInputType.TIME
            )
        )

        val textInputVerticalList = listOf(
            Input(
                interviewLabel = InterviewLabel.COMPANY, required = true,
                errorTextId = R.string.error_message_form_input_company,
                inputType = TextInputType.TEXT
            ),
            Input(
                interviewLabel = InterviewLabel.INTERVIEW_TYPE,
                inputType = TextInputType.DROPDOWN
            ),
            Input(
                interviewLabel = InterviewLabel.ROLE,
                inputType = TextInputType.DROPDOWN
            ),
            Input(
                interviewLabel = InterviewLabel.ROUND,
                inputType = TextInputType.TEXT
            ),
            Input(
                interviewLabel = InterviewLabel.JOB_POST,
                inputType = TextInputType.TEXT
            ),
            Input(
                interviewLabel = InterviewLabel.COMPANY_LINK,
                inputType = TextInputType.TEXT
            ),
            Input(
                interviewLabel = InterviewLabel.INTERVIEWER,
                inputType = TextInputType.TEXT
            )
        )
    }
}
data class OnEditInterview(
    val dateYMD: String = "",
    val dateHM: String = "",
    val company: String = "",
    val interviewType: String = "",
    val role: String = "",
    val roundNum: Int? = null,
    val jobPostLink: String = "",
    val companyLink: String = "",
    val interviewer: String = "",
    val interviewStatus: InterviewStatus = InterviewStatus.NO_UPDATE
)

fun OnEditInterview.toDatabaseModel(): Interview = Interview(
    date = SimpleDateFormat(
        "MM/dd/yyyy hh:mm",
        Locale.getDefault()
    ).parse("$dateYMD $dateHM") ?: Date(),
    company = company,
    interviewType = interviewType,
    role = role,
    roundNum = roundNum,
    jobPostLink = jobPostLink,
    companyLink = companyLink,
    interviewer = interviewer,
    interviewStatus = interviewStatus
)

fun OnEditInterview.isValid() = listOf(dateHM, dateYMD, company).none { it.isEmpty() }

fun OnEditInterview.getInterviewField(label: InterviewLabel) = when (label) {
    InterviewLabel.DATE -> dateYMD
    InterviewLabel.TIME -> dateHM
    InterviewLabel.COMPANY -> company
    InterviewLabel.INTERVIEW_TYPE -> interviewType
    InterviewLabel.ROLE -> role
    InterviewLabel.ROUND -> roundNum?.toString().orEmpty()
    InterviewLabel.JOB_POST -> jobPostLink
    InterviewLabel.COMPANY_LINK -> companyLink
    InterviewLabel.INTERVIEWER -> interviewer
}

data class Input(
    val value: String = "",
    val interviewLabel: InterviewLabel,
    @StringRes val bottomTextId: Int? = null,
    val required: Boolean = false,
    @StringRes val errorTextId: Int? = null,
    val inputType: TextInputType = TextInputType.TEXT
) {
    @StringRes
    fun labelTextId() = when (interviewLabel) {
        InterviewLabel.DATE -> R.string.hint_label_date
        InterviewLabel.TIME -> R.string.hint_label_time
        InterviewLabel.COMPANY -> R.string.hint_label_company
        InterviewLabel.INTERVIEW_TYPE -> R.string.hint_label_interview_type
        InterviewLabel.ROLE -> R.string.hint_label_role
        InterviewLabel.ROUND -> R.string.hint_label_round_count
        InterviewLabel.JOB_POST -> R.string.hint_label_job_post
        InterviewLabel.COMPANY_LINK -> R.string.hint_label_company_link
        InterviewLabel.INTERVIEWER -> R.string.hint_label_interviewer
    }
}

enum class TextInputType {
    TEXT,
    DATE,
    TIME,
    DROPDOWN
}

enum class InterviewLabel {
    DATE, TIME, COMPANY, INTERVIEW_TYPE, ROLE, ROUND, JOB_POST, COMPANY_LINK, INTERVIEWER
}
