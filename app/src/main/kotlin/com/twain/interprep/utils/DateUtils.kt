package com.twain.interprep.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import com.twain.interprep.helper.Constants
import android.icu.util.Calendar
import java.util.Locale

class DateUtils {
    companion object{
        fun convertDateToMilliseconds(dateString: String): Long {
            val format = SimpleDateFormat(Constants.DT_FORMAT_MM_DD_YYYY, Locale.getDefault())
            val date = format.parse(dateString)
            return date?.time ?: 0L
        }

        fun getCurrentDateAsString(): String {
            val format = SimpleDateFormat(Constants.DT_FORMAT_MM_DD_YYYY, Locale.getDefault())
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
    }
}