package com.twain.interprep.domain.usecase.interview

import com.twain.interprep.data.model.Interview
import com.twain.interprep.domain.repository.InterviewRepository

class DeleteInterviewUseCase(private val interviewRepository: InterviewRepository) {

    suspend operator fun invoke(interview: Interview) {
        interviewRepository.deleteInterview(interview)
    }
}
