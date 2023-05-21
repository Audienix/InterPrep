package com.twain.interprep.utils

import android.icu.text.SimpleDateFormat
import com.twain.interprep.helper.Constants
import java.util.Calendar
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
    }
}