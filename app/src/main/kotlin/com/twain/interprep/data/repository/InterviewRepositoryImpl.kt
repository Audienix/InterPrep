package com.twain.interprep.data.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.InterviewDAO
import com.twain.interprep.data.model.Interview
import com.twain.interprep.domain.repository.InterviewRepository
import kotlinx.coroutines.flow.Flow

class InterviewRepositoryImpl(private val interviewDao: InterviewDAO) : InterviewRepository {

    val allInterviews: Flow<List<Interview>> = interviewDao.getAllInterviews()

    @WorkerThread
    override suspend fun insertInterview(interview: Interview) {
        interviewDao.insertInterview(interview)
    }

    @WorkerThread
    override suspend fun updateInterview(interview: Interview) {
        interviewDao.updateInterview(interview)
    }

    @WorkerThread
    override fun getInterviews(): Flow<List<Interview>> = interviewDao.getAllInterviews()

    @WorkerThread
    override suspend fun getInterviewById(id: Int): Interview? = interviewDao.getInterview(id = id)

    @WorkerThread
    override suspend fun deleteInterview(interview: Interview) {
        interviewDao.deleteInterview(interview)
    }

    @WorkerThread
    override suspend fun deleteAllInterviews() {
        interviewDao.deleteAllInterviews()
    }
}