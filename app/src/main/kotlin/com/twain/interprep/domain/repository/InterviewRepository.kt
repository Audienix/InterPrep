package com.twain.interprep.domain.repository

import com.twain.interprep.data.model.Interview
import kotlinx.coroutines.flow.Flow

interface InterviewRepository {

    suspend fun insertInterview(interview: Interview)

    suspend fun updateInterview(interview: Interview)

    suspend fun getInterviews(): Flow<List<Interview>>

    suspend fun getInterviewById(id: Int): Flow<Interview>

    suspend fun deleteInterview(interview: Interview)

    suspend fun deleteAllInterviews()
}
