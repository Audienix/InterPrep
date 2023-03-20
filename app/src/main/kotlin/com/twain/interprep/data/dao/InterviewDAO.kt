package com.twain.interprep.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.twain.interprep.data.model.Interview

interface InterviewDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterview(interview: Interview)

    @Update
    suspend fun updateInterview(interview: Interview)

    @Delete
    suspend fun deleteInterview(interview: Interview)
}