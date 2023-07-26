package com.twain.interprep.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.twain.interprep.presentation.ui.modules.common.MainScreen
import com.twain.interprep.presentation.ui.modules.introduction.IntroScreen

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