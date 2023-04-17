package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.twain.interprep.helper.Constants.Companion.DB_TABLE_INTERVIEWS
import java.util.Calendar

@Entity(tableName = DB_TABLE_INTERVIEWS)
data class Interview(
    @PrimaryKey(autoGenerate = true) val interviewId: Int,
    val date: Calendar,
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
    NOUPDATE,
    NEXTROUND,
    REJECTED,
    SELECTED
}

class CalendarTypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long): Calendar? {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = value
        return calendar
    }

    @TypeConverter
    fun toTimestamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }
}
