package com.twain.interprep.data.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twain.interprep.R
import com.twain.interprep.constants.StringConstants.Companion.DB_TABLE_INTERVIEW
import com.twain.interprep.presentation.ui.theme.BackgroundDarkGray
import com.twain.interprep.presentation.ui.theme.BackgroundDarkGreen
import com.twain.interprep.presentation.ui.theme.BackgroundDarkPurple
import com.twain.interprep.presentation.ui.theme.BackgroundLightGray
import com.twain.interprep.presentation.ui.theme.BackgroundLightGreen
import com.twain.interprep.presentation.ui.theme.BackgroundLightPurple

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
    val interviewStatus: InterviewStatus = InterviewStatus.NO_UPDATE
)

data class DashBoardInterviews(
    var isEmptyInterviewList: Boolean,
    val upcomingInterviews: MutableList<Interview>,
    val comingNextInterviews: MutableList<Interview>,
    val pastInterviews: MutableList<Interview>
)

enum class InterviewStatus {
    NO_UPDATE,
    NEXT_ROUND,
    REJECTED,
    SELECTED
}

sealed class DashboardInterviewType(
    val cardBackgroundColor: Color,
    val cardContentColor: Color,
    val cardWidthFactor: Float
) {
    class UpcomingInterview :
        DashboardInterviewType(BackgroundLightPurple, BackgroundDarkPurple, 0.8f)

    class NextInterview :
        DashboardInterviewType(BackgroundLightGreen, BackgroundDarkGreen, 0.8f)

    class PastInterview :
        DashboardInterviewType(BackgroundLightGray, BackgroundDarkGray, 1f)
}

fun Interview.isValid() = listOf(date, time, company).none { it.isEmpty() }

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
