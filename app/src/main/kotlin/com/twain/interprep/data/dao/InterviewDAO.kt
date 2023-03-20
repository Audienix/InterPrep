package com.twain.interprep.data.dao

import androidx.room.*
import com.twain.interprep.data.model.Interview

@Dao
interface InterviewDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterview(interview: Interview)

    @Update
    suspend fun updateInterview(interview: Interview)

    @Delete
    suspend fun deleteInterview(interview: Interview)
}