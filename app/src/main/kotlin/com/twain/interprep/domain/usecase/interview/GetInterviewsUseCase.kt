package com.twain.interprep.domain.usecase.interview

import android.icu.text.SimpleDateFormat
import com.twain.interprep.constants.StringConstants.DT_FORMAT_MM_DD_YYYY_HH_MM_A
import com.twain.interprep.data.model.DashboardInterviews
import com.twain.interprep.data.model.Interview
import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.utils.DateUtils
import com.twain.interprep.utils.DateUtils.isInterviewDateInSameWeek
import kotlinx.coroutines.flow.transform
import java.util.Date
import java.util.Locale

class GetInterviewsUseCase(private val interviewRepository: InterviewRepository) {

    suspend operator fun invoke() =
        interviewRepository.getInterviews().transform { interviews ->
            val dashboardInterviews =
                DashboardInterviews(
                    isEmptyInterviewList = true,
                    upcomingInterviews = mutableListOf(),
                    comingNextInterviews = mutableListOf(),
                    pastInterviews = mutableListOf()
                )
            dashboardInterviews.isEmptyInterviewList = interviews.isEmpty()

            createDashboardInterviews(interviews, dashboardInterviews)
            emit(dashboardInterviews)
        }

    /**
    Creates dashboard interviews based on the given interview list queried from the database.
    The function categorizes the interviews into past, upcoming, and coming next interviews.
    It sorts the interviews based on date and time.
    @param interviewListFromDB The list of interviews retrieved from the database.
    @param dashboardInterviews The object to store the categorized and sorted interviews.
     */
    private fun createDashboardInterviews(
        interviewListFromDB: List<Interview>,
        dashboardInterviews: DashboardInterviews
    ) {
        val sortedUpcomingInterviews = mutableListOf<Interview>()
        val sortedComingNextInterviews = mutableListOf<Interview>()
        val sortedPastInterviews = mutableListOf<Interview>()
        interviewListFromDB.onEach { interview ->
            val date = DateUtils.convertDateTimeStringToDate(interview.date, interview.time)

            if (date.before(Date())) {
                sortedPastInterviews.add(interview)
            } else if (isInterviewDateInSameWeek(date)) {
                sortedUpcomingInterviews.add(interview)
            } else {
                sortedComingNextInterviews.add(interview)
            }
        }
        val dateFormat = SimpleDateFormat(DT_FORMAT_MM_DD_YYYY_HH_MM_A, Locale.getDefault())

        dashboardInterviews.pastInterviews =
            sortedPastInterviews.sortedWith(compareBy {
                dateFormat.parse(
                    "${it.date} ${it.time}"
                )
            }).toMutableList()

        dashboardInterviews.comingNextInterviews =
            sortedComingNextInterviews.sortedWith(compareBy {
                dateFormat.parse(
                    "${it.date} ${it.time}"
                )
            }).toMutableList()

        dashboardInterviews.upcomingInterviews =
            sortedUpcomingInterviews.sortedWith(compareBy {
                dateFormat.parse(
                    "${it.date} ${it.time}"
                )
            }).toMutableList()
    }
}
