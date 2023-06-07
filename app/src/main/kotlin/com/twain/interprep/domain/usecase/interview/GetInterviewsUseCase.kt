package com.twain.interprep.domain.usecase.interview

import com.twain.interprep.data.model.Interview
import com.twain.interprep.domain.repository.InterviewRepository
import kotlinx.coroutines.flow.transform
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GetInterviewsUseCase(private val interviewRepository: InterviewRepository) {

    operator fun invoke() =
        interviewRepository.getInterviews().transform { interviews ->
            val dashBoardInterviews =
                DashBoardInterviews(mutableListOf(), mutableListOf(), mutableListOf())
            interviews.onEach { interview ->
                if (interview.date.before(Date())) {
                    dashBoardInterviews.pastInterviews.add(interview)
                } else if (isInterviewDateInSameWeek(interview.date)) {
                    dashBoardInterviews.upcomingInterviews.add(interview)
                } else {
                    dashBoardInterviews.comingNextInterviews.add(interview)
                }
            }
            emit(dashBoardInterviews)
        }


    private fun isInterviewDateInSameWeek(date: Date): Boolean {
        val currentDateTime = Calendar.getInstance(Locale.getDefault())
        val interviewDateTime = Calendar.getInstance(Locale.getDefault())

        currentDateTime.time = Date()
        interviewDateTime.time = date

        val currentWeek = currentDateTime.get(Calendar.WEEK_OF_YEAR)
        val comparedWeek = interviewDateTime.get(Calendar.WEEK_OF_YEAR)

        val currentYear = currentDateTime.get(Calendar.YEAR)
        val comparedYear = interviewDateTime.get(Calendar.YEAR)

        return currentWeek == comparedWeek && currentYear == comparedYear
    }

    data class DashBoardInterviews(
        val upcomingInterviews: MutableList<Interview>,
        val comingNextInterviews: MutableList<Interview>,
        val pastInterviews: MutableList<Interview>
    )
}
