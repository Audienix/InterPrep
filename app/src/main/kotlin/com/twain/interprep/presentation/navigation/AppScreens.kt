package com.twain.interprep.presentation.navigation

import com.google.gson.Gson

sealed class AppScreens(val route: String) {
    //Route for Intro Screen
    object IntroScreen : AppScreens("introduction_screen")

    // Nested Route for Main Application Screens (Dashboard, Notes, Resources)
    object MainScreens : AppScreens("main"){
        object Dashboard : AppScreens("dashboard_screen")
        object AddInterview : AppScreens("add_interview_screen")
        object InterviewDetails : AppScreens("interview_details_screen")

        object Notes : AppScreens("notes_screen")
        object AddNotes : AppScreens("add_notes_screen")

        object ViewNotes: AppScreens("view_notes_screen")

        object ViewMoreQuestions: AppScreens("view_more_questions")

        object Resources : AppScreens("resources_screen")
    }
    object Resources : AppScreens("resources_screen")
    object AddResource : AppScreens("add_resource_screen")

    fun withArgs(vararg args: Any): String{
        return buildString { append(route)
        args.forEach { arg->
            append("/${arg.takeUnless { arg is List<*> } ?: Gson().toJson(arg)}")
        }}
    }
}
