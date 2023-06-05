package com.twain.interprep.data.ui

import com.twain.interprep.data.model.Interview
import com.twain.interprep.data.model.InterviewStatus
import java.util.Calendar

val calendar: Calendar = Calendar.getInstance()
val interviewMockData = Interview(
    interviewId = 1,
    date = calendar.time,
    company = "Uber",
    interviewType = "In-person",
    role = "Software Engineer",
    roundNum = 2,
    jobPostLink = "https://www.linkedin.com/jobs/collections/recommended/?currentJobId=3512066424",
    companyLink = "https://www.uber.com/ca/en/ride/",
    interviewer = "John Smith",
    interviewStatus = InterviewStatus.NEXT_ROUND
)