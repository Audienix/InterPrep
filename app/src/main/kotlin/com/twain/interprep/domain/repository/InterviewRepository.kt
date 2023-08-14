package com.twain.interprep.domain.repository

import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewListMetaData
import com.twain.interprep.data.model.InterviewType
import kotlinx.coroutines.flow.Flow

interface InterviewRepository {

    suspend fun insertInterview(interview: Interview)

    suspend fun updateInterview(interview: Interview)

    suspend fun getTodayInterviews(): Flow<List<Interview>>
    suspend fun getInitialInterviews(): Flow<InterviewListMetaData>

    suspend fun getInterviewList(type: InterviewType, page: Int): Flow<List<Interview>>

    suspend fun getInterviewById(id: Int): Flow<Interview>

    suspend fun deleteInterview(interview: Interview)

    suspend fun deleteAllInterviews()
}
