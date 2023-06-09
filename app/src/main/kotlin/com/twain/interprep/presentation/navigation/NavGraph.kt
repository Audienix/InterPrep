package com.twain.interprep.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.twain.interprep.constants.StringConstants.NAV_ARG_INTERVIEW_ID
import com.twain.interprep.constants.StringConstants.NAV_ARG_IS_EDIT
import com.twain.interprep.constants.StringConstants.NAV_ARG_PRIMARY_COLOR
import com.twain.interprep.constants.StringConstants.NAV_ARG_SECONDARY_COLOR
import com.twain.interprep.presentation.ui.modules.dashboard.DashboardScreen
import com.twain.interprep.presentation.ui.modules.interview.AddInterviewScreen
import com.twain.interprep.presentation.ui.modules.interview.InterviewDetailsScreen
import com.twain.interprep.presentation.ui.modules.notes.AddNotesScreen
import com.twain.interprep.presentation.ui.modules.notes.NotesScreen
import com.twain.interprep.presentation.ui.modules.notes.ViewNotesScreen
import com.twain.interprep.presentation.ui.modules.resources.ResourcesScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppScreens.Dashboard.route) {
        // Dashboard Screen
        composable(AppScreens.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        // Add Interview Screen
        composable(route = "${AppScreens.AddInterview.route}/{interviewId}", arguments = listOf(
            navArgument(NAV_ARG_INTERVIEW_ID) {
                type = NavType.IntType
            }
        )) { entry ->
            val interviewId = entry.arguments?.getInt(NAV_ARG_INTERVIEW_ID)
            AddInterviewScreen(navController = navController, interviewId = interviewId ?: 0)
        }
        // Interview Details Screen
        composable(
            route = "${AppScreens.InterviewDetails.route}/{interviewId}/{primaryColor}/{secondaryColor}",
            arguments = getInterviewDetailsNavArguments()
        ) { entry ->
            NavigateToInterviewDetails(entry, navController)
        }
        // Notes List Screen
        composable(AppScreens.Notes.route) {
            NotesScreen(navController = navController)
        }
        // Add Notes Screen
        composable(
            route = "${AppScreens.AddNotes.route}/{interviewId}/{isEdit}",
            arguments = getAddNotesNavArguments()
        ) { entry ->
            NavigateToAddNotes(entry, navController)
        }
        // View Notes Screen
        composable(route = "${AppScreens.ViewNotes.route}/{interviewId}", arguments = listOf(
            navArgument("interviewId") {
                type = NavType.IntType
            }
        )) { entry ->
            val interviewId = entry.arguments?.getInt(NAV_ARG_INTERVIEW_ID) ?: 0
            ViewNotesScreen(navController = navController, interviewId = interviewId)
        }
        // Resource List Screen
        composable(AppScreens.Resources.route) {
            ResourcesScreen()
        }
    }
}

@Composable
private fun NavigateToAddNotes(
    entry: NavBackStackEntry,
    navController: NavHostController
) {
    val interviewId = entry.arguments?.getInt(NAV_ARG_INTERVIEW_ID) ?: 0
    val isEdit = entry.arguments?.getBoolean(NAV_ARG_IS_EDIT) ?: false
    AddNotesScreen(
        navController = navController,
        interviewId = interviewId,
        isEdit = isEdit
    )
}

private fun getAddNotesNavArguments() = listOf(
    navArgument(NAV_ARG_INTERVIEW_ID) {
        type = NavType.IntType
    },
    navArgument(NAV_ARG_IS_EDIT) {
        type = NavType.BoolType
    }
)

@Composable
private fun NavigateToInterviewDetails(
    entry: NavBackStackEntry,
    navController: NavHostController
) {
    val interviewId = entry.arguments?.getInt(NAV_ARG_INTERVIEW_ID)
    val primaryColor = entry.arguments?.getInt(NAV_ARG_PRIMARY_COLOR)?.let { Color(it) }
    val secondaryColor = entry.arguments?.getInt(NAV_ARG_SECONDARY_COLOR)?.let { Color(it) }
    primaryColor?.let { primary ->
        secondaryColor?.let { secondary ->
            InterviewDetailsScreen(
                navController = navController,
                interviewId = interviewId,
                primaryColor = primary,
                secondaryColor = secondary
            )
        }
    }
}

private fun getInterviewDetailsNavArguments() = listOf(
    navArgument(NAV_ARG_INTERVIEW_ID) {
        type = NavType.IntType
    },
    navArgument(NAV_ARG_PRIMARY_COLOR) {
        type = NavType.IntType
    },
    navArgument(NAV_ARG_SECONDARY_COLOR) {
        type = NavType.IntType
    }
)
