package com.twain.interprep.presentation.navigation




sealed class AppScreens(val route: String) {
    object Dashboard : AppScreens("dashboard_screen")
    object Notes : AppScreens("notes_screen")
    object Resources : AppScreens("resources_screen")
    object AddInterview : AppScreens("add_interview_screen")
    object InterviewDetails : AppScreens("interview_details_screen")
}