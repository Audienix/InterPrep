package com.twain.interprep.domain.usecase.interview

import com.twain.interprep.domain.repository.InterviewRepository

class GetInterviewByIdUseCase(private val interviewRepository: InterviewRepository) {

    suspend operator fun invoke(id: Int) {
        interviewRepository.getInterviewById(id)
    }
}
