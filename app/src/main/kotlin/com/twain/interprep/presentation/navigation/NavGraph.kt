package com.twain.interprep.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.twain.interprep.presentation.ui.modules.dashboard.AddInterviewScreen
import com.twain.interprep.presentation.ui.modules.dashboard.DashboardScreen
import com.twain.interprep.presentation.ui.modules.interview.InterviewDetailsScreen
import com.twain.interprep.presentation.ui.modules.interview.InterviewViewModel
import com.twain.interprep.presentation.ui.modules.notes.NotesScreen
import com.twain.interprep.presentation.ui.modules.resources.ResourcesScreen

@Composable
fun NavGraph(navController: NavHostController) {

    val interviewViewModel: InterviewViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = AppScreens.Dashboard.route) {
        composable(AppScreens.Dashboard.route) {
            DashboardScreen(navController = navController, viewModel = interviewViewModel)
        }
        composable(AppScreens.Notes.route) {
            NotesScreen()
        }
        composable(AppScreens.Resources.route) {
            ResourcesScreen()
        }
        composable(AppScreens.AddInterview.route) {
            AddInterviewScreen(navController = navController, viewModel = interviewViewModel)
        }
        composable(AppScreens.InterviewDetails.route){
            InterviewDetailsScreen(navController = navController, viewModel = interviewViewModel)
        }
    }
}