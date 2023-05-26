package com.twain.interprep.data.dao

import androidx.room.*
import com.twain.interprep.data.model.Interview
import kotlinx.coroutines.flow.Flow

@Dao
interface InterviewDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInterview(interview: Interview)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInterview(interview: Interview)

    @Query("SELECT * FROM interview WHERE interviewId = :id")
    fun getInterview(id: Int): Interview

    @Query("SELECT * FROM interview ORDER BY date DESC ")
    fun getAllInterviews(): Flow<List<Interview>>

    @Delete
    suspend fun deleteInterview(interview: Interview)

    @Query("DELETE FROM interview")
    suspend fun deleteAllInterviews()
}