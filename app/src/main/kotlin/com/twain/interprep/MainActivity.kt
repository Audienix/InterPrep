package com.twain.interprep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.twain.interprep.data.model.ViewResult
import com.twain.interprep.data.ui.QuoteData
import com.twain.interprep.helper.LocalizationViewModel
import com.twain.interprep.datastore.usecase.DataStoreUseCase
import com.twain.interprep.helper.BooleanPair
import com.twain.interprep.helper.PrefManager
import com.twain.interprep.presentation.navigation.OnboardingNavGraph
import com.twain.interprep.presentation.ui.modules.interview.QuotesViewModel
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var prefManager: PrefManager
    @Inject
    lateinit var localizationViewModel: LocalizationViewModel
    @Inject
    lateinit var dataStoreUseCase: DataStoreUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val themeArray = resources.getStringArray(R.array.theme_option)
            var dakTheme by rememberSaveable { mutableStateOf(themeArray[2]) }
            LaunchedEffect(Unit) {
                dataStoreUseCase.getAppThemeUseCase().collect {
                    dakTheme = it
                }
            }
            InterPrepTheme(
                darkTheme = when (dakTheme) {
                    themeArray[1] -> true
                    themeArray[2] -> isSystemInDarkTheme()
                    else -> false
                }
            ) {
                localizationViewModel.initialize(this)
                InsertQuotesInDB()

                // A surface container using the 'surface' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialColorPalette.surface
                ) {
                    val navController = rememberNavController()
                    OnboardingNavGraph(
                        navController = navController, hasOnboarded = prefManager.getBoolean(
                            BooleanPair.HasOnboarded
                        )
                    )
                }
            }
        }
    }

    @Composable
    private fun InsertQuotesInDB() {
        // Insert quotes into DB
        val quotesViewModel: QuotesViewModel = hiltViewModel()
        LaunchedEffect(Unit) {
            quotesViewModel.insertQuotes(QuoteData.quotes)
        }
    }
}
