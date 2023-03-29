package com.twain.interprep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Calendar

@Entity
data class Interview (
    @PrimaryKey(autoGenerate = true)
    var interviewId: Int = 0,
    var interviewDateTime: Long = Calendar.getInstance().timeInMillis,
)

//@Entity(tableName = "interviews")
//data class Interview(
//    @PrimaryKey(autoGenerate = true) val interviewId: Int,
//    val date: Calendar,
//    val company: String,
//    val interviewType: String?,
//    val role: String?,
//    val roundNum: Int?,
//    val jobPostLink: String?,
//    val companyLink: String?,
//    val interviewer: String?,
//    val interviewStatus: InterviewStatus
//)

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
