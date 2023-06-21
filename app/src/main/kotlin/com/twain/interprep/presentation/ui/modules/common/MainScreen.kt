package com.twain.interprep.presentation.ui.modules.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.twain.interprep.data.ui.QuoteData
import com.twain.interprep.presentation.navigation.BottomNavigationBar
import com.twain.interprep.presentation.navigation.NavGraph
import com.twain.interprep.presentation.ui.modules.interview.QuotesViewModel

@Composable
fun MainScreen(
    quotesViewModel: QuotesViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        // TODO: check if this should be MainScreen or MainActivity
        quotesViewModel.insertQuotes(QuoteData.quotes)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavGraph(navController = navController)
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
