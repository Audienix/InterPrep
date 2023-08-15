package com.twain.interprep.data.repository

import androidx.annotation.WorkerThread
import com.twain.interprep.constants.NumberConstants.INTERVIEW_PAGE_LIMIT
import com.twain.interprep.data.dao.InterviewDAO
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewList
import com.twain.interprep.data.model.InterviewListMetaData
import com.twain.interprep.data.model.InterviewType
import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class InterviewRepositoryImpl(private val interviewDao: InterviewDAO) : InterviewRepository {

    @WorkerThread
    override suspend fun insertInterview(interview: Interview) {
        interviewDao.insertInterview(interview)
    }

    @WorkerThread
    override suspend fun updateInterview(interview: Interview) {
        interviewDao.updateInterview(interview)
    }

    @WorkerThread
    override suspend fun getInitialInterviews(): Flow<InterviewListMetaData> {

        val dateCurr = DateUtils.getCurrentDateTimeAsString()
        val dateFuture = DateUtils.getWeekAfterCurrentDateAsString()

        val pastInterviews =  interviewDao.getPastInterviews(dateCurr = dateCurr)
        val upcomingInterviews = interviewDao.getUpcomingInterviews(dateCurr = dateCurr, dateFuture = dateFuture)
        val compingNextInterviews = interviewDao.getComingNextInterviews(dateFuture = dateFuture)

        return combine(pastInterviews, upcomingInterviews, compingNextInterviews) { past, upcoming, comingNext ->
            InterviewListMetaData(
                pastInterviewList = InterviewList(
                    list = past, page = 0, hasMore = past.size == INTERVIEW_PAGE_LIMIT
                ),
                upcomingInterviewList = InterviewList(
                    list = upcoming, page = 0, hasMore = upcoming.size == INTERVIEW_PAGE_LIMIT
                ),
                comingNextInterviewList = InterviewList(
                    list = comingNext, page = 0, hasMore = comingNext.size == INTERVIEW_PAGE_LIMIT
                )
            )
        }
    }

    @WorkerThread
    override suspend fun getInterviewList(type: InterviewType, page: Int): Flow<List<Interview>> {
        val dateCurr = DateUtils.getCurrentDateTimeAsString()
        val dateFuture = DateUtils.getWeekAfterCurrentDateAsString()
        val offset = page * INTERVIEW_PAGE_LIMIT
        return when (type) {
            InterviewType.PAST -> interviewDao.getPastInterviews(
                dateCurr = dateCurr,
                offset = offset
            )
            InterviewType.PRESENT -> interviewDao.getUpcomingInterviews(
                dateCurr = dateCurr,
                dateFuture = dateFuture,
                offset = offset
            )
            InterviewType.FUTURE -> interviewDao.getComingNextInterviews(
                dateFuture = dateFuture,
                offset = offset
            )
        }
    }
    @WorkerThread
    override suspend fun getTodayInterviews(): Flow<List<Interview>> {
        val currentDateTime = DateUtils.getCurrentDateTimeAsString()
        val endOfDay = DateUtils.getEndOfDayAsString()
        return interviewDao.getTodayInterviews(currentDateTime = currentDateTime, endOfDay = endOfDay)
    }
    @WorkerThread
    override suspend fun getInterviewById(id: Int): Flow<Interview> =
        interviewDao.getInterview(id = id)

    @WorkerThread
    override suspend fun deleteInterview(interview: Interview) {
        interviewDao.deleteInterview(interview)
    }

    @WorkerThread
    override suspend fun deleteAllInterviews() {
        interviewDao.deleteAllInterviews()
    }
}
