package com.twain.interprep.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.twain.interprep.presentation.ui.modules.common.MainScreen
import com.twain.interprep.presentation.ui.modules.introduction.IntroScreen
import androidx.navigation.navArgument
import com.twain.interprep.constants.StringConstants.NAV_ARG_INTERVIEW_ID
import com.twain.interprep.constants.StringConstants.NAV_ARG_IS_EDIT
import com.twain.interprep.constants.StringConstants.NAV_ARG_PRIMARY_COLOR
import com.twain.interprep.constants.StringConstants.NAV_ARG_RESOURCE_ID
import com.twain.interprep.constants.StringConstants.NAV_ARG_SECONDARY_COLOR
import com.twain.interprep.presentation.ui.modules.interview.AddInterviewScreen
import com.twain.interprep.presentation.ui.modules.interview.InterviewDetailsScreen
import com.twain.interprep.presentation.ui.modules.notes.AddNotesScreen
import com.twain.interprep.presentation.ui.modules.notes.NotesScreen
import com.twain.interprep.presentation.ui.modules.notes.ViewNotesScreen
import com.twain.interprep.presentation.ui.modules.resources.AddResourceScreen
import com.twain.interprep.presentation.ui.modules.resources.ResourcesScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppScreens.IntroScreen.route) {
        composable(AppScreens.IntroScreen.route) {
            IntroScreen(navController = navController)
        }
        // MainScreen will have its own Navgraph to manage the bottom bar
        composable(AppScreens.MainScreens.route){
            MainScreen()
        }
    }
}