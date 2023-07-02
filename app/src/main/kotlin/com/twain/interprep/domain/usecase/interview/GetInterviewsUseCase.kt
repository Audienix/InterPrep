package com.twain.interprep.domain.usecase.interview

import com.twain.interprep.data.model.DashboardInterviews
import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.utils.DateUtils
import com.twain.interprep.utils.DateUtils.isInterviewDateInSameWeek
import kotlinx.coroutines.flow.transform
import java.util.Date

class GetInterviewsUseCase(private val interviewRepository: InterviewRepository) {

    suspend operator fun invoke() =
        interviewRepository.getInterviews().transform { interviews ->
            val dashBoardInterviews =
                DashboardInterviews(
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
}
