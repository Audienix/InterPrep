package com.twain.interprep.data.dao

import androidx.room.*
import com.twain.interprep.data.model.Interview
import com.twain.interprep.helper.Constants
import com.twain.interprep.helper.Constants.Companion.DB_TABLE_INTERVIEWS

@Dao
interface InterviewDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterview(interview: Interview)

    @Update
    suspend fun updateInterview(interview: Interview)

    @Delete
    suspend fun deleteInterview(interview: Interview)

    @Query("SELECT * FROM $DB_TABLE_INTERVIEWS WHERE interviewId = :id")
    fun getInterview(id: Int): Interview
}