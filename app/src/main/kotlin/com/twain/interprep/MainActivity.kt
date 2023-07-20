package com.twain.interprep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.twain.interprep.data.ui.QuoteData
import com.twain.interprep.presentation.navigation.NavGraph
import com.twain.interprep.presentation.ui.modules.common.MainScreen
import com.twain.interprep.presentation.ui.modules.interview.QuotesViewModel
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            InterPrepTheme {
                // Insert quotes into DB
                val quotesViewModel: QuotesViewModel = hiltViewModel()
                LaunchedEffect(Unit) {
                    quotesViewModel.insertQuotes(QuoteData.quotes)
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                    MainScreen()
                }
            }
        }
    }
}
