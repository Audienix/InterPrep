package com.twain.interprep.domain.usecase.interview

import com.twain.interprep.data.model.Interview
import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.util.InterviewsData
import kotlinx.coroutines.flow.transform
import java.util.Calendar
import java.util.Date

class GetInterviewsUseCase(private val interviewRepository: InterviewRepository) {

    operator fun invoke() =
        interviewRepository.getInterviews().transform { interviews ->
            val dashBoardInterviews =
                DashBoardInterviews(mutableListOf(), mutableListOf(), mutableListOf())
            InterviewsData.interviews.onEach { interview ->
                if (interview.date.before(Date())) {
                    dashBoardInterviews.pastInterviews.add(interview)
                } else if (isSameWeekAsCurrentDate(interview.date)) {
                    dashBoardInterviews.upcomingInterviews.add(interview)
                } else {
                    dashBoardInterviews.comingNextInterviews.add(interview)
                }
            }
            emit(dashBoardInterviews)
        }


    private fun isSameWeekAsCurrentDate(date: Date): Boolean {
        val current = Calendar.getInstance()
        val comparedCalendar = Calendar.getInstance()

        current.time = Date()
        comparedCalendar.time = date

        val currentWeek = current.get(Calendar.WEEK_OF_YEAR)
        val comparedWeek = comparedCalendar.get(Calendar.WEEK_OF_YEAR)

        val currentYear = current.get(Calendar.YEAR)
        val comparedYear = comparedCalendar.get(Calendar.YEAR)

        return currentWeek == comparedWeek && currentYear == comparedYear
    }

    data class DashBoardInterviews(
        val upcomingInterviews: MutableList<Interview>,
        val comingNextInterviews: MutableList<Interview>,
        val pastInterviews: MutableList<Interview>
    )
}
