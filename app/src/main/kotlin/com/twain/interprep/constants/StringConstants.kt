package com.twain.interprep.constants

object StringConstants {
    const val SHARED_PREF_NAME = "interprep_shared_pref"

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

    // Chrome Package
    const val CHROME_PACKAGE_NAME = "com.android.chrome"

    // Privacy Policy Website
    const val PRIVACY_POLICY_WEBSITE = "https://medium.com/@team.interprep/interprep-privacy-policy-9a270cb96626"
}
