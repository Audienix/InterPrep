package com.twain.interprep.domain.repository

import com.twain.interprep.data.model.Interview
import kotlinx.coroutines.flow.Flow

interface InterviewRepository {

    suspend fun insertInterview(interview: Interview)

    suspend fun updateInterview(interview: Interview)

    fun getInterviews(): Flow<List<Interview>>

    suspend fun getInterviewById(id: Int): Interview?

    suspend fun deleteInterview(interview: Interview)

    suspend fun deleteAllInterviews()
}