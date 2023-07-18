package com.twain.interprep.domain.usecase.interview

data class InterviewUseCase(
    val insertInterview: InsertInterviewUseCase,
    val updateInterview: UpdateInterviewUseCase,
    val getInterviews: GetInterviewsUseCase,
    val getTypedInterviews: GetTypedInterviewsUseCase,
    val getInterviewById: GetInterviewByIdUseCase,
    val deleteInterview: DeleteInterviewUseCase,
    val deleteAllInterviews: DeleteAllInterviewsUseCase,
)
