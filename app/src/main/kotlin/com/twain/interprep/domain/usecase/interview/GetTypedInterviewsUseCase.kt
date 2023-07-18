package com.twain.interprep.domain.usecase.interview

import com.twain.interprep.data.dao.PAGE_LIMIT
import com.twain.interprep.data.model.InterviewList
import com.twain.interprep.data.model.InterviewListMetaData
import com.twain.interprep.data.model.InterviewType
import com.twain.interprep.domain.repository.InterviewRepository
import kotlinx.coroutines.flow.transform

class GetTypedInterviewsUseCase(private val interviewRepository: InterviewRepository) {
    suspend operator fun invoke(
        type: InterviewType,
        page: Int,
        currentState: InterviewListMetaData
    ) =
        interviewRepository.getTypedInterviews(
            type = type,
            page = page
        ).transform { interviews ->

            val hasMore = interviews.size == PAGE_LIMIT

            emit(
                when (type) {
                    InterviewType.PAST -> currentState.copy(
                        pastInterviewList = InterviewList(
                            list = currentState.pastInterviewList.list + interviews,
                            page = page,
                            hasMore = hasMore
                        )
                    )

                    InterviewType.UPCOMING -> currentState.copy(
                        upcomingInterviewList = InterviewList(
                            list = currentState.upcomingInterviewList.list + interviews,
                            page = page,
                            hasMore = hasMore
                        )
                    )

                    InterviewType.COMING_NEXT -> currentState.copy(
                        comingNextInterviewList = InterviewList(
                            list = currentState.comingNextInterviewList.list + interviews,
                            page = page,
                            hasMore = hasMore
                        )
                    )
                }
            )
        }
}