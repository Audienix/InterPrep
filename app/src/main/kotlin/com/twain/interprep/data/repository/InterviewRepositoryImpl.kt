package com.twain.interprep.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import com.twain.interprep.data.dao.InterviewDAO
import com.twain.interprep.data.dao.PAGE_LIMIT
import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewList
import com.twain.interprep.data.model.InterviewListMetaData
import com.twain.interprep.data.model.InterviewType
import com.twain.interprep.domain.repository.InterviewRepository
import com.twain.interprep.utils.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class InterviewRepositoryImpl(private val interviewDao: InterviewDAO) : InterviewRepository {

    @WorkerThread
    override suspend fun insertInterview(interview: Interview) {
        interviewDao.insertInterview(interview)
    }

    @WorkerThread
    override suspend fun updateInterview(interview: Interview) {
        interviewDao.updateInterview(interview)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @WorkerThread
    override suspend fun getInitialInterviews(): Flow<InterviewListMetaData> {

        val currentTime = LocalTime.now()
        val formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        val dateCurr = DateUtils.getCurrentDateAsString() + formattedTime
        val dateFuture = DateUtils.getWeekAfterCurrentDateAsString() + formattedTime

        val pastInterviews =  interviewDao.getPastInterviews(dateCurr = dateCurr)
        val upcomingInterviews = interviewDao.getUpcomingInterviews(dateCurr = dateCurr, dateFuture = dateFuture)
        val compingNextInterviews = interviewDao.getComingNextInterviews(dateFuture = dateFuture)

        return combine(pastInterviews, upcomingInterviews, compingNextInterviews) { past, upcoming, comingNext ->
            InterviewListMetaData(
                pastInterviewList = InterviewList(
                    list = past, page = 0, hasMore = past.size == PAGE_LIMIT
                ),
                upcomingInterviewList = InterviewList(
                    list = upcoming, page = 0, hasMore = upcoming.size == PAGE_LIMIT
                ),
                comingNextInterviewList = InterviewList(
                    list = comingNext, page = 0, hasMore = comingNext.size == PAGE_LIMIT
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @WorkerThread
    override suspend fun getTypedInterviews(type: InterviewType, page: Int): Flow<List<Interview>> {
        val currentTime = LocalTime.now()
        val formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        val dateCurr = DateUtils.getCurrentDateAsString() + formattedTime
        val dateFuture = DateUtils.getWeekAfterCurrentDateAsString() + formattedTime
        val offset = page * PAGE_LIMIT
        return when(type) {
            InterviewType.PAST -> interviewDao.getPastInterviews(
                dateCurr = dateCurr,
                offset = offset
            )
            InterviewType.UPCOMING -> interviewDao.getUpcomingInterviews(
                dateCurr = dateCurr,
                dateFuture = dateFuture,
                offset = offset
            )
            InterviewType.COMING_NEXT -> interviewDao.getComingNextInterviews(
                dateFuture = dateFuture,
                offset = offset
            )
        }
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
