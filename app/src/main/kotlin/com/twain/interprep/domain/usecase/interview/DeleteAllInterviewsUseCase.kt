package com.twain.interprep.domain.usecase.interview

import com.twain.interprep.domain.repository.InterviewRepository

class DeleteAllInterviewsUseCase (private val interviewRepository: InterviewRepository) {

    suspend operator fun invoke() {
        interviewRepository.deleteAllInterviews()
    }
}
