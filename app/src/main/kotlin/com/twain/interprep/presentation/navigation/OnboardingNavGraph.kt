package com.twain.interprep.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.twain.interprep.presentation.ui.modules.common.MainScreen
import com.twain.interprep.presentation.ui.modules.onboarding.GreetingScreen
import com.twain.interprep.presentation.ui.modules.onboarding.IntroScreen
import com.twain.interprep.presentation.ui.modules.onboarding.OnboardingViewModel

@Composable
fun OnboardingNavGraph(
    navController: NavHostController,
    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {
    val startDestinationScreen = if (onboardingViewModel.getOnboardingStatus()
            .equals(false)
    ) AppScreens.OnboardingScreens.Introduction.route else AppScreens.MainScreens.route
    NavHost(
        navController = navController,
        route = AppScreens.OnboardingScreens.route,
        startDestination = startDestinationScreen
    ) {
        composable(AppScreens.OnboardingScreens.Introduction.route) {
            IntroScreen {
                navController.navigate(AppScreens.OnboardingScreens.Greetings.route) {
                    popUpTo(AppScreens.OnboardingScreens.route) { inclusive = true }
                }
            }
        }
        composable(AppScreens.OnboardingScreens.Greetings.route) {
            GreetingScreen(navController = navController)
        }
        // MainScreen will have its own Navgraph to manage the bottom bar
        composable(AppScreens.MainScreens.route) {
            MainScreen()
        }
    }
}
