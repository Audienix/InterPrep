package com.twain.interprep.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.twain.interprep.constants.NumberConstants.INTERVIEW_PAGE_LIMIT
import com.twain.interprep.data.model.Interview
import kotlinx.coroutines.flow.Flow

@Dao
interface InterviewDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInterview(interview: Interview)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInterview(interview: Interview)

    @Query("SELECT * FROM interview WHERE interviewId = :id")
    fun getInterview(id: Int): Flow<Interview>

    @Query("SELECT * FROM interview WHERE (date || time) < :dateCurr ORDER BY (date || time) LIMIT :limit OFFSET :offset")
    fun getPastInterviews(
        dateCurr: String,
        limit: Int = INTERVIEW_PAGE_LIMIT,
        offset: Int = 0
    ): Flow<List<Interview>>

    @Query("SELECT * FROM interview WHERE (date || time) >= :dateCurr AND (date || time) <= :dateFuture ORDER BY (date || time) LIMIT :limit OFFSET :offset")
    fun getUpcomingInterviews(
        dateCurr: String,
        dateFuture: String,
        limit: Int = INTERVIEW_PAGE_LIMIT,
        offset: Int = 0
    ): Flow<List<Interview>>

    @Query("SELECT * FROM interview WHERE (date || time) > :dateFuture ORDER BY (date || time) LIMIT :limit OFFSET :offset")
    fun getComingNextInterviews(
        dateFuture: String,
        limit: Int = INTERVIEW_PAGE_LIMIT,
        offset: Int = 0
    ): Flow<List<Interview>>

    @Delete
    suspend fun deleteInterview(interview: Interview)

    @Query("DELETE FROM interview")
    suspend fun deleteAllInterviews()
}
