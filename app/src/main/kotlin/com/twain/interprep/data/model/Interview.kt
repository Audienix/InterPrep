package com.twain.interprep.data.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twain.interprep.R
import com.twain.interprep.constants.NumberConstants.CARD_FULL_WIDTH_FACTOR
import com.twain.interprep.constants.NumberConstants.CARD_PARTIAL_WIDTH_FACTOR
import com.twain.interprep.constants.StringConstants.DB_TABLE_INTERVIEW
import com.twain.interprep.presentation.ui.theme.BackgroundDarkGray
import com.twain.interprep.presentation.ui.theme.BackgroundDarkGreen
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundLightGray
import com.twain.interprep.presentation.ui.theme.BackgroundLightGreen
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple
import com.twain.interprep.presentation.ui.theme.StatusNextRound
import com.twain.interprep.presentation.ui.theme.StatusNoUpdate
import com.twain.interprep.presentation.ui.theme.StatusRejected
import com.twain.interprep.presentation.ui.theme.StatusSelected
import com.twain.interprep.utils.DateUtils
import com.twain.interprep.utils.isValidURL
import java.util.Date


@Entity(tableName = DB_TABLE_INTERVIEW)
data class Interview(
    @PrimaryKey(autoGenerate = true) val interviewId: Int = 0,
    val date: String = "",
    val time: String = "",
    val company: String = "",
    val interviewType: String = "",
    val role: String = "",
    val roundNum: String = "",
    val jobPostLink: String = "",
    val companyLink: String = "",
    val interviewer: String = "",
    val interviewStatus: InterviewStatus = InterviewStatus.NO_UPDATE,
)

enum class InterviewStatus(
    private val mResourceId: Int,
    private val mStatusColor: Color
) {
    NO_UPDATE(
        mResourceId = R.string.interview_status_no_update,
        mStatusColor = StatusNoUpdate
    ),
    NEXT_ROUND(
        mResourceId = R.string.interview_status_next_round,
        mStatusColor = StatusNextRound
    ),
    REJECTED(
        mResourceId = R.string.interview_status_rejected,
        mStatusColor = StatusRejected
    ),
    SELECTED(
        mResourceId = R.string.interview_status_selected,
        mStatusColor = StatusSelected
    );

    fun getResourceId(): Int = mResourceId
    fun getSecondaryColor(): Color = mStatusColor
}

sealed class DashboardInterviewType(
    val cardBackgroundColor: Color,
    val cardContentColor: Color,
    val cardWidthFactor: Float
) {
    class UpcomingInterview :
        DashboardInterviewType(
            BackgroundLightPurple,
            BackgroundDarkPurple,
            CARD_PARTIAL_WIDTH_FACTOR
        )

    class NextInterview :
        DashboardInterviewType(BackgroundLightGreen, BackgroundDarkGreen, CARD_PARTIAL_WIDTH_FACTOR)

    class PastInterview :
        DashboardInterviewType(BackgroundLightGray, BackgroundDarkGray, CARD_FULL_WIDTH_FACTOR)
}

fun Interview.isValid(): Boolean {
    val requiredInfoValid = listOf(date, time, company).all { it.isNotEmpty() }
    val linksValid = listOf(jobPostLink, companyLink).all { it.isBlank() or isValidURL(it) }
    return requiredInfoValid and linksValid
}

fun Interview.getInterviewField(@StringRes labelTextId: Int) = when (labelTextId) {
    R.string.hint_label_date -> date
    R.string.hint_label_time -> time
    R.string.hint_label_company -> company
    R.string.hint_label_interview_type -> interviewType
    R.string.hint_label_role -> role
    R.string.hint_label_round_count -> roundNum
    R.string.hint_label_job_post -> jobPostLink
    R.string.hint_label_company_link -> companyLink
    R.string.hint_label_interviewer -> interviewer
    else -> {
        ""
    }
}

fun Interview.isPast() =
    false.takeIf { date.isEmpty() } ?: DateUtils.convertDateTimeStringToDate(date, time)
        .before(Date())
