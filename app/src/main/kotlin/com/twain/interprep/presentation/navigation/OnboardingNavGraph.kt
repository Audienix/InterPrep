package com.twain.interprep.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.twain.interprep.helper.BooleanPair
import com.twain.interprep.helper.PrefManager
import com.twain.interprep.presentation.ui.modules.common.MainScreen
import com.twain.interprep.presentation.ui.modules.onboarding.GreetingScreen
import com.twain.interprep.presentation.ui.modules.onboarding.IntroScreen

@Composable
fun OnboardingNavGraph (
    navController: NavHostController,
    prefManager: PrefManager
) {
    NavHost(
        navController = navController,
        route = AppScreens.OnboardingScreens.route,
        startDestination = AppScreens.OnboardingScreens.Introduction.route.takeUnless {
            prefManager.getBoolean(BooleanPair.HasOnboarded)
        }
            ?: AppScreens.MainScreens.route) {
        composable(AppScreens.OnboardingScreens.Introduction.route) {
            IntroScreen {
                navController.navigate(AppScreens.OnboardingScreens.Greetings.route) {
                    popUpTo(AppScreens.OnboardingScreens.route) { inclusive = true }
                }
            }
        }
        composable(AppScreens.OnboardingScreens.Greetings.route) {
            GreetingScreen(navController = navController, prefManager = prefManager)
        }
        // MainScreen will have its own NavGraph to manage the bottom bar
        composable(AppScreens.MainScreens.route) {
            MainScreen()
        }
    }
}
