package com.twain.interprep.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard_screen")
    object Notes : Screen("notes_screen")
    object Resources : Screen("resources_screen")
}