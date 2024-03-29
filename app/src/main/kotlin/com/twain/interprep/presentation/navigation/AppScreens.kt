package com.twain.interprep.presentation.navigation

sealed class AppScreens(val route: String) {
    //Route for Onboarding Screens
    object OnboardingScreens : AppScreens("onboarding"){
        object Introduction: AppScreens("introduction_screen")
        object Greetings : AppScreens("greetings_screen")
    }

    // Nested Route for Main Application Screens (Dashboard, Notes, Resources)
    object MainScreens : AppScreens("main") {
        object Dashboard : AppScreens("dashboard_screen")
        object AddInterview : AppScreens("add_interview_screen")
        object InterviewDetails : AppScreens("interview_details_screen")

        object Notes : AppScreens("notes_screen")
        object AddNotes : AppScreens("add_notes_screen")
        object ViewNotes : AppScreens("view_notes_screen")
        object ViewMoreQuestions : AppScreens("view_more_questions")

        object Resources : AppScreens("resources_screen")
        object AddResource : AppScreens("add_resource_screen")

        object Profile : AppScreens("profile")

        object PrivacyPolicy: AppScreens("privacy_policy")
    }

    fun withArgs(vararg args: Any): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
