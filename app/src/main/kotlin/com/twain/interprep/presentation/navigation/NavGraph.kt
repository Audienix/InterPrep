package com.twain.interprep.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.twain.interprep.presentation.ui.modules.dashboard.DashboardScreen
import com.twain.interprep.presentation.ui.modules.interview.AddInterviewScreen
import com.twain.interprep.presentation.ui.modules.interview.InterviewDetailsScreen
import com.twain.interprep.presentation.ui.modules.notes.AddNotesScreen
import com.twain.interprep.presentation.ui.modules.notes.NotesScreen
import com.twain.interprep.presentation.ui.modules.resources.ResourcesScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = AppScreens.Dashboard.route) {


        // interview

        composable(AppScreens.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        composable(route = "${AppScreens.AddInterview.route}/{interviewId}", arguments = listOf(
            navArgument("interviewId") {
                type = NavType.IntType
            }
        )) { entry ->
            val interviewId = entry.arguments?.getInt("interviewId")
            AddInterviewScreen(navController = navController, interviewId = interviewId ?: 0)
        }
        composable(
            route = "${AppScreens.InterviewDetails.route}/{interviewId}/{primaryColor}/{secondaryColor}",
            arguments = listOf(
                navArgument("interviewId") {
                    type = NavType.IntType
                },
                navArgument("primaryColor") {
                    type = NavType.IntType
                },
                navArgument("secondaryColor") {
                    type = NavType.IntType
                }
            )
        ) { entry ->
            val interviewId = entry.arguments?.getInt("interviewId")
            val primaryColor = entry.arguments?.getInt("primaryColor")?.let { Color(it) }
            val secondaryColor = entry.arguments?.getInt("secondaryColor")?.let { Color(it) }
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

        // note

        composable(AppScreens.Notes.route) {
            NotesScreen(navController = navController)
        }

        composable(route = "${AppScreens.AddNotes.route}/{interviewId}", arguments = listOf(
            navArgument("interviewId") {
                type = NavType.IntType
            }
        )) { entry ->
            val interviewId = entry.arguments?.getInt("interviewId")
            AddNotesScreen(navController = navController, interviewId = interviewId ?: 0)
        }
        // resource

        composable(AppScreens.Resources.route) {
            ResourcesScreen()
        }
    }
}
