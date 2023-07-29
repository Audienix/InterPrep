package com.twain.interprep.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.twain.interprep.helper.BooleanPair
import com.twain.interprep.helper.PrefManager
import com.twain.interprep.presentation.ui.modules.common.MainScreen
import com.twain.interprep.presentation.ui.modules.introduction.IntroScreen

@Composable
fun NavGraph(navController: NavHostController, prefManager: PrefManager) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.IntroScreen.route.takeUnless {
            prefManager.getBoolean(BooleanPair.IsIntroScreenShown) }
            ?: AppScreens.MainScreens.route) {
        composable(AppScreens.IntroScreen.route) {
            IntroScreen{
                prefManager.putBoolean(PrefManager.IS_INTRO_SHOWN, true)
                // Open Dashboard, and remove the Intro Screen from Stack
                navController.navigate(AppScreens.MainScreens.route){
                    popUpTo(AppScreens.IntroScreen.route) { inclusive = true }
                }
            }
        }
        // MainScreen will have its own Navgraph to manage the bottom bar
        composable(AppScreens.MainScreens.route){
            MainScreen()
        }
    }
}
