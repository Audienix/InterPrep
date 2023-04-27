package com.twain.interprep.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.InterviewDAO
import com.twain.interprep.data.model.Interview
import kotlinx.coroutines.flow.Flow

class InterviewRepository(private val interviewDao: InterviewDAO) {

    val allInterviews: Flow<List<Interview>> = interviewDao.getAllInterviews()

    @WorkerThread
    suspend fun insert(interview: Interview) {
        interviewDao.insertInterview(interview)
    }

    @WorkerThread
    suspend fun delete(interview: Interview) {
        interviewDao.deleteInterview(interview)
    }

    @WorkerThread
    suspend fun update(interview: Interview) {
        interviewDao.updateInterview(interview)
    }
}