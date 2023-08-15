package com.twain.interprep.constants

object NumberConstants {
    const val SECOND_IN_MILLISECONDS = 1000
    const val MINUTE_IN_SECONDS = 60
    const val HOUR_IN_MINUTES = 60
    private const val DAY_IN_HOURS = 24
    const val WEEK_IN_DAYS = 7
    const val WEEK_IN_MILLISECONDS =
        WEEK_IN_DAYS * DAY_IN_HOURS * HOUR_IN_MINUTES * MINUTE_IN_SECONDS * SECOND_IN_MILLISECONDS

    const val PENDING_INTENT_REQUEST_CODE = 123

    const val INTERVIEW_PAGE_LIMIT = 20
}
