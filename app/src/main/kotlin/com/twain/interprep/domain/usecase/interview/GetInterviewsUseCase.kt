package com.twain.interprep.domain.usecase.interview

import com.twain.interprep.domain.repository.InterviewRepository
import kotlinx.coroutines.flow.transform

class GetInterviewsUseCase(private val interviewRepository: InterviewRepository) {

    suspend operator fun invoke() =
        interviewRepository.getInitialInterviews().transform { interviews ->
            emit(interviews)
        }
}
