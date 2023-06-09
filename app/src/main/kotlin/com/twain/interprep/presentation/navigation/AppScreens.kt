package com.twain.interprep.presentation.navigation

sealed class AppScreens(val route: String) {
    object Dashboard : AppScreens("dashboard_screen")
    object AddInterview : AppScreens("add_interview_screen")
    object InterviewDetails : AppScreens("interview_details_screen")

    object Notes : AppScreens("notes_screen")
    object AddNotes : AppScreens("add_notes_screen")

    object ViewNotes: AppScreens("view_notes_screen")

    object Resources : AppScreens("resources_screen")

    fun withArgs(vararg args: Any): String{
        return buildString { append(route)
        args.forEach { arg->
            append("/$arg")
        }}
    }
}
