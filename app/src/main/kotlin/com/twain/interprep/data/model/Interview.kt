package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.twain.interprep.helper.Constants.Companion.DB_TABLE_INTERVIEWS
import java.util.*

@Entity(tableName = DB_TABLE_INTERVIEWS)
data class Interview(
    @PrimaryKey(autoGenerate = true) val interviewId: Int,
    val date: Date,
    val company: String,
    val interviewType: String?,
    val role: String?,
    val roundNum: Int?,
    val jobPostLink: String?,
    val companyLink: String?,
    val interviewer: String?,
    val interviewStatus: InterviewStatus
)

enum class InterviewStatus {
    NO_UPDATE,
    NEXT_ROUND,
    REJECTED,
    SELECTED
}