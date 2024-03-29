package com.twain.interprep.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.twain.interprep.constants.StringConstants
import com.twain.interprep.data.model.InterviewType
import com.twain.interprep.presentation.ui.modules.dashboard.DashboardScreen
import com.twain.interprep.presentation.ui.modules.interview.AddInterviewScreen
import com.twain.interprep.presentation.ui.modules.interview.InterviewDetailsScreen
import com.twain.interprep.presentation.ui.modules.notes.AddNotesScreen
import com.twain.interprep.presentation.ui.modules.notes.NotesScreen
import com.twain.interprep.presentation.ui.modules.notes.ViewMoreQuestionsScreen
import com.twain.interprep.presentation.ui.modules.notes.ViewNotesScreen
import com.twain.interprep.presentation.ui.modules.profile.PrivacyPolicyScreen
import com.twain.interprep.presentation.ui.modules.profile.ProfileScreen
import com.twain.interprep.presentation.ui.modules.resources.AddResourceScreen
import com.twain.interprep.presentation.ui.modules.resources.ResourcesScreen

// Main Dashboard NavGraph.
@Composable
fun MainScreensNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = AppScreens.MainScreens.route,
        startDestination = AppScreens.MainScreens.Dashboard.route
    ) {
        // Dashboard Screen
        composable(AppScreens.MainScreens.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        // Add Interview Screen
        composable(route = "${AppScreens.MainScreens.AddInterview.route}/{interviewId}",
            arguments = listOf(
                navArgument(StringConstants.NAV_ARG_INTERVIEW_ID) {
                    type = NavType.IntType
                }
            )) { entry ->
            val interviewId = entry.arguments?.getInt(StringConstants.NAV_ARG_INTERVIEW_ID)
            AddInterviewScreen(navController = navController, interviewId = interviewId ?: 0)
        }
        // Interview Details Screen
        composable(
            route = "${AppScreens.MainScreens.InterviewDetails.route}/{interviewId}/{interviewType}",
            arguments = getInterviewDetailsNavArguments()
        ) { entry ->
            NavigateToInterviewDetails(entry, navController)
        }
        // Notes List Screen
        composable(AppScreens.MainScreens.Notes.route) {
            NotesScreen(navController = navController)
        }
        // Add Notes Screen
        composable(
            route = "${AppScreens.MainScreens.AddNotes.route}/{interviewId}/{isEdit}",
            arguments = getAddNotesNavArguments()
        ) { entry ->
            NavigateToAddNotes(entry, navController)
        }
        // View Notes Screen
        composable(route = "${AppScreens.MainScreens.ViewNotes.route}/{interviewId}",
            arguments = listOf(
                navArgument("interviewId") {
                    type = NavType.IntType
                }
            )) { entry ->
            val interviewId = entry.arguments?.getInt(StringConstants.NAV_ARG_INTERVIEW_ID) ?: 0
            ViewNotesScreen(navController = navController, interviewId = interviewId)
        }
        // View More Questions
        composable(
            route = "${AppScreens.MainScreens.ViewMoreQuestions.route}/{noteIndex}/{noteId}",
            arguments = listOf(
                navArgument("noteIndex") {
                    type = NavType.IntType
                },
                navArgument("noteId") {
                    type = NavType.IntType
                },
            )
        ) { entry ->
            val noteIndex = entry.arguments?.getInt("noteIndex") ?: 0
            val noteId = entry.arguments?.getInt("noteId") ?: 0
            ViewMoreQuestionsScreen(
                noteId = noteId,
                noteIndex = noteIndex,
                navController = navController
            )
        }
        // Resource List Screen
        composable(AppScreens.MainScreens.Resources.route) {
            ResourcesScreen(navController = navController)
        }
        // Add Resource Screen
        composable(
            route = "${AppScreens.MainScreens.AddResource.route}/{resourceId}",
            arguments = getAddResourceNavArguments()
        ) { entry ->
            NavigateToAddResource(entry, navController)
        }

        // Profile
        composable(
            route = AppScreens.MainScreens.Profile.route
        ) {
            ProfileScreen(navController = navController)
        }

        // privacy policy
        composable(
            route = AppScreens.MainScreens.PrivacyPolicy.route
        ) {
            PrivacyPolicyScreen()
        }
    }
}

@Composable
private fun NavigateToAddNotes(
    entry: NavBackStackEntry,
    navController: NavHostController
) {
    val interviewId = entry.arguments?.getInt(StringConstants.NAV_ARG_INTERVIEW_ID) ?: 0
    val isEdit = entry.arguments?.getBoolean(StringConstants.NAV_ARG_IS_EDIT) ?: false
    AddNotesScreen(
        navController = navController,
        interviewId = interviewId,
        isEdit = isEdit
    )
}

private fun getAddNotesNavArguments() = listOf(
    navArgument(StringConstants.NAV_ARG_INTERVIEW_ID) {
        type = NavType.IntType
    },
    navArgument(StringConstants.NAV_ARG_IS_EDIT) {
        type = NavType.BoolType
    }
)

@Composable
private fun NavigateToInterviewDetails(
    entry: NavBackStackEntry,
    navController: NavHostController
) {
    val interviewId = entry.arguments?.getInt(StringConstants.NAV_ARG_INTERVIEW_ID)
    val interviewType = entry.arguments?.getString(StringConstants.NAV_ARG_INTERVIEW_TYPE).toString()
    InterviewDetailsScreen(
        navController = navController,
        interviewId = interviewId,
        interviewType = InterviewType.valueOf(interviewType)
    )

}

private fun getInterviewDetailsNavArguments() = listOf(
    navArgument(StringConstants.NAV_ARG_INTERVIEW_ID) {
        type = NavType.IntType
    },
    navArgument(StringConstants.NAV_ARG_INTERVIEW_TYPE) {
        type = NavType.StringType
    }
)

@Composable
private fun NavigateToAddResource(
    entry: NavBackStackEntry,
    navController: NavHostController
) {
    val resourceId = entry.arguments?.getInt(StringConstants.NAV_ARG_RESOURCE_ID) ?: 0
    AddResourceScreen(
        navController = navController,
        resourceId = resourceId
    )
}

private fun getAddResourceNavArguments() = listOf(
    navArgument(StringConstants.NAV_ARG_RESOURCE_ID) {
        type = NavType.IntType
    }
)
