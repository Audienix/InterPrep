package com.twain.interprep.domain.usecase.interview

import com.twain.interprep.data.model.DashBoardInterviews
import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.utils.DateUtils
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.flow.transform

class GetInterviewsUseCase(private val interviewRepository: InterviewRepository) {

    suspend operator fun invoke() =
        interviewRepository.getInterviews().transform { interviews ->
            val dashBoardInterviews =
                DashBoardInterviews(
                    isEmptyInterviewList = true,
                    upcomingInterviews = mutableListOf(),
                    comingNextInterviews = mutableListOf(),
                    pastInterviews = mutableListOf()
                )
            dashBoardInterviews.isEmptyInterviewList = interviews.isEmpty()
            interviews.onEach { interview ->
                val date = DateUtils.convertDateTimeStringToDate(interview.date, interview.time)

                if (date.before(Date())) {
                    dashBoardInterviews.pastInterviews.add(interview)
                } else if (isInterviewDateInSameWeek(date)) {
                    dashBoardInterviews.upcomingInterviews.add(interview)
                } else {
                    dashBoardInterviews.comingNextInterviews.add(interview)
                }
            }
            emit(dashBoardInterviews)
        }

    /**
     * @return true iff the given date is in the same week as the current week
     */
    private fun isInterviewDateInSameWeek(date: Date): Boolean {
        val c: Calendar = Calendar.getInstance()
        c.firstDayOfWeek = Calendar.MONDAY
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)

        val monday: Date = c.time
        val nextMonday = Date(monday.time + 7 * 24 * 60 * 60 * 1000)
        return date.after(monday) && date.before(nextMonday)
    }
}
