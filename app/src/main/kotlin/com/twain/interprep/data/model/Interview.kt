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
import com.twain.interprep.presentation.ui.theme.StatusNextRoundPrimary
import com.twain.interprep.presentation.ui.theme.StatusNextRoundSecondary
import com.twain.interprep.presentation.ui.theme.StatusNoUpdatePrimary
import com.twain.interprep.presentation.ui.theme.StatusNoUpdateSecondary
import com.twain.interprep.presentation.ui.theme.StatusRejectedPSecondary
import com.twain.interprep.presentation.ui.theme.StatusRejectedPrimary
import com.twain.interprep.presentation.ui.theme.StatusSelectedPrimary
import com.twain.interprep.presentation.ui.theme.StatusSelectedSecondary
import com.twain.interprep.utils.DateUtils
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

data class DashboardInterviews(
    var isEmptyInterviewList: Boolean,
    var upcomingInterviews: MutableList<Interview>,
    var comingNextInterviews: MutableList<Interview>,
    var pastInterviews: MutableList<Interview>
)

enum class InterviewStatus(
    private val mResourceId: Int,
    private val mPrimaryColor: Color,
    private val mSecondaryColor: Color
) {
    NO_UPDATE(
        mResourceId = R.string.interview_status_no_update,
        mPrimaryColor = StatusNoUpdatePrimary,
        mSecondaryColor = StatusNoUpdateSecondary
    ),
    NEXT_ROUND(
        mResourceId = R.string.interview_status_next_round,
        mPrimaryColor = StatusNextRoundPrimary,
        mSecondaryColor = StatusNextRoundSecondary
    ),
    REJECTED(
        mResourceId = R.string.interview_status_rejected,
        mPrimaryColor = StatusRejectedPrimary,
        mSecondaryColor = StatusRejectedPSecondary
    ),
    SELECTED(
        mResourceId = R.string.interview_status_selected,
        mPrimaryColor = StatusSelectedPrimary,
        mSecondaryColor = StatusSelectedSecondary
    );

    fun getResourceId(): Int = mResourceId
    fun getPrimaryColor(): Color = mPrimaryColor
    fun getSecondaryColor(): Color = mSecondaryColor
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

fun Interview.isPast() =
    false.takeIf { date.isEmpty() } ?: DateUtils.convertDateTimeStringToDate(date, time)
        .before(Date())
