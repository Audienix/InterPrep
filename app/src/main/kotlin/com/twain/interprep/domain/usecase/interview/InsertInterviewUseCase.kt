package com.twain.interprep.domain.usecase.interview

import com.twain.interprep.data.model.Interview
import com.twain.interprep.domain.repository.InterviewRepository

class InsertInterviewUseCase (private val interviewRepository: InterviewRepository) {

    suspend operator fun invoke(interview: Interview) {
        interviewRepository.insertInterview(interview)
    }
}
