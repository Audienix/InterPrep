package com.twain.interprep.presentation.ui.modules.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.navigation.BottomNavigationBar
import com.twain.interprep.presentation.navigation.MainScreensNavGraph
import com.twain.interprep.presentation.navigation.NavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentScreen in listOf(
                    AppScreens.MainScreens.Dashboard.route,
                    AppScreens.MainScreens.Notes.route,
                    AppScreens.MainScreens.Resources.route
            )) BottomNavigationBar(navController)
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                MainScreensNavGraph(navController = navController)
            }
        },
        // Set background color to avoid the white flashing when you switch between screens
        containerColor = MaterialTheme.colorScheme.background
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
