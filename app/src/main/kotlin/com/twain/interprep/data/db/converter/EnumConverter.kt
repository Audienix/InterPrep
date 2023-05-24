package com.twain.interprep.data.db.converter

import androidx.room.TypeConverter
import com.twain.interprep.data.model.InterviewStatus

class EnumConverter {

    @TypeConverter
    fun fromInterviewStatus(interviewStatus: InterviewStatus) = interviewStatus.toString()

    @TypeConverter
    fun toInterviewStatus(status: String) =  InterviewStatus.valueOf(status)
}
