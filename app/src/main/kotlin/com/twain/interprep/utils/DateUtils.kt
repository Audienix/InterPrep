package com.twain.interprep.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import com.twain.interprep.constants.NumberConstants.MILLISECONDS
import com.twain.interprep.constants.NumberConstants.WEEK_IN_MILLISECONDS
import com.twain.interprep.constants.StringConstants.DT_FORMAT_HOUR_MIN
import com.twain.interprep.constants.StringConstants.DT_FORMAT_MM_DD_YYYY
import com.twain.interprep.constants.StringConstants.DT_FORMAT_MM_DD_YYYY_HH_MM_A
import java.util.Date
import java.util.Locale

object DateUtils {
    fun convertDateToMilliseconds(dateString: String): Long {
        val format = SimpleDateFormat(DT_FORMAT_MM_DD_YYYY, Locale.getDefault())
        val date = format.parse(dateString)
        return date?.time ?: 0L
    }

    fun convertDateStringToDate(dateString: String): Date {
        val format = SimpleDateFormat(DT_FORMAT_MM_DD_YYYY, Locale.getDefault())
        return format.parse(dateString)
    }

    fun convertDateTimeStringToDate(dateString: String, timeString: String): Date {
        val format = SimpleDateFormat(
            "$DT_FORMAT_MM_DD_YYYY $DT_FORMAT_HOUR_MIN",
            Locale.getDefault()
        )
        return format.parse("$dateString $timeString")
    }

    fun getCurrentDateAsString(): String {
        val format = SimpleDateFormat(DT_FORMAT_MM_DD_YYYY, Locale.getDefault())
        return format.format(Calendar.getInstance().time)
    }

    //TODO Fix the issue of current date getting disabled when current UTC time falls next day
    fun isValidDate(utcDateInMills: Long): Boolean {
        val utcTimeZone = TimeZone.getTimeZone("UTC")

        val currentUtcCalendar = Calendar.getInstance(utcTimeZone).apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        val givenUtcCalendar = Calendar.getInstance(utcTimeZone).apply {
            timeInMillis = utcDateInMills
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        return givenUtcCalendar >= currentUtcCalendar
    }

    /**
     * @return true if the given date is in the same week as the current week
     */
    fun isInterviewDateInSameWeek(date: Date): Boolean {
        val c: java.util.Calendar = java.util.Calendar.getInstance()
        c.firstDayOfWeek = java.util.Calendar.SUNDAY
        c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SUNDAY)
        c.set(java.util.Calendar.HOUR_OF_DAY, 0)
        c.set(java.util.Calendar.MINUTE, 0)
        c.set(java.util.Calendar.SECOND, 0)
        c.set(java.util.Calendar.MILLISECOND, 0)

        val monday: Date = c.time
        val nextMonday = Date(monday.time + WEEK_IN_MILLISECONDS)
        return date.after(monday) && date.before(nextMonday)
    }

    fun isAM(timeString: String): Boolean {
        val format = SimpleDateFormat("a", Locale.getDefault())
        val date = format.parse(timeString)
        return date?.hours in 0..11
    }

    fun calculateTimeDifferenceInSeconds(
        targetDate: String,
        targetTime: String,
        reminderTimeBefore: Int
    ): Long {
        val dateFormat = SimpleDateFormat(DT_FORMAT_MM_DD_YYYY_HH_MM_A, Locale.getDefault())
        val currentDate = Date()
        val targetDateTime = dateFormat.parse("$targetDate $targetTime")

        // Calculate the time difference in milliseconds
        val timeDifferenceInMillis = targetDateTime.time - currentDate.time

        // Convert the time difference to seconds
        return (timeDifferenceInMillis / MILLISECONDS) - reminderTimeBefore
    }
}
