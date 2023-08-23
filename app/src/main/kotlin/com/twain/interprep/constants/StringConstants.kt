package com.twain.interprep.constants

object StringConstants {
    // All date & time formats supported
    const val DT_FORMAT_DATE = "dd"
    const val DT_FORMAT_MONTH_YEAR = "MMM, yyyy"
    const val DT_FORMAT_DAY = "EEE"
    const val DT_FORMAT_MM_DD_YYYY_HH_MM_A = "MM/dd/yyyy hh:mm a"
    const val DT_FORMAT_MM_DD_YYYY_HH_MM = "MM/dd/yyyy HH:mm"
    const val DT_FORMAT_MM_DD_YYYY_HH_MM_NO_SPACE = "MM/dd/yyyyHH:mm"
    const val DT_FORMAT_HOUR_MIN = "HH:mm"
    const val DT_FORMAT_HOUR_MIN_A = "hh:mm a"
    const val DT_FORMAT_MM_DD_YYYY = "MM/dd/yyyy"
    const val DT_FORMAT_DD_MMMM_YYYY = "dd MMMM, yyyy"

    // Database table/entity names
    const val DB_TABLE_INTERVIEW = "interview"
    const val DB_TABLE_NOTE = "note"
    const val DB_TABLE_QUESTION = "question"
    const val DB_TABLE_QUOTE = "quote"
    const val DB_TABLE_RESOURCE = "resource"
    const val DB_TABLE_RESOURCE_LINKS = "resource_link"
    const val DB_TABLE_SUBTOPICS = "subtopic"
    const val DB_TABLE_TOPICS = "topic"

    // Screen navigation arguments
    const val NAV_ARG_INTERVIEW_ID = "interviewId"
    const val NAV_ARG_INTERVIEW_TYPE = "interviewType"
    const val NAV_ARG_IS_EDIT = "isEdit"
    const val NAV_ARG_RESOURCE_ID = "resourceId"

    // Notification keys
    const val NOTIFICATION_KEY_TITLE = "title"
    const val NOTIFICATION_KEY_MESSAGE = "message"

    // Language
    const val ENGLISH_US_CODE = "en_US"
    const val ENGLISH_US_DROPDOWN_NAME = "English (US)"
    const val ENGLISH_US_LABEL = "English"

    // AppTheme
    const val SYSTEM_NAME = "System"
    const val DARK_MODE_NAME = "Dark"
    const val LIGHT_MODE_NAME = "Light"

    const val SYSTEM_LABEL = "Sync with System"
    const val DARK_MODE_LABEL = "Dark Mode"
    const val LIGHT_MODE_LABEL = "Light Mode"

    // notification
    const val MIN_15_BEFORE = "15 minutes before"
    const val MIN_30_BEFORE = "30 minutes before"
    const val HOUR_1_BEFORE = "1 hour before"
    const val NO_NOTIFICATION = "No notification"

}
