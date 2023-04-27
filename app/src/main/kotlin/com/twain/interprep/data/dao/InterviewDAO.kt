package com.twain.interprep.data.dao

import androidx.room.*
import com.twain.interprep.data.model.Interview
import com.twain.interprep.util.StringConstants.Companion.DB_TABLE_INTERVIEWS
import kotlinx.coroutines.flow.Flow

@Dao
interface InterviewDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterview(interview: Interview)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInterview(interview: Interview)

    @Delete
    suspend fun deleteInterview(interview: Interview)

    // Question: Do we need this method?
    @Query("SELECT * FROM $DB_TABLE_INTERVIEWS WHERE interviewId = :id")
    fun getInterview(id: Int): Interview

    @Query("SELECT * FROM $DB_TABLE_INTERVIEWS")
    fun getAllInterviews(): Flow<List<Interview>>
}