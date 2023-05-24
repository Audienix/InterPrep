package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.twain.interprep.constants.StringConstants.Companion.DB_TABLE_INTERVIEW
import java.util.*

@Entity(tableName = DB_TABLE_INTERVIEW)
data class Interview(
    @PrimaryKey(autoGenerate = true) val interviewId: Int,
    val date: Date,
    val company: String,
    val interviewType: String? = "",
    val role: String? = "",
    val roundNum: Int? = null,
    val jobPostLink: String? = "",
    val companyLink: String? = "",
    val interviewer: String? = "",
    val interviewStatus: InterviewStatus = InterviewStatus.NO_UPDATE
)

enum class InterviewStatus {
    NO_UPDATE,
    NEXT_ROUND,
    REJECTED,
    SELECTED
}