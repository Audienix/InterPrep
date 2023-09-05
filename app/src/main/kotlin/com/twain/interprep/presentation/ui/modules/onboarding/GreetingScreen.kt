package com.twain.interprep.presentation.ui.modules.onboarding


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.twain.interprep.R
import com.twain.interprep.data.ui.TextInputAttributes
import com.twain.interprep.data.ui.TextInputType
import com.twain.interprep.helper.LocalizationViewModel
import com.twain.interprep.presentation.navigation.AppScreens
import com.twain.interprep.presentation.ui.components.generic.IPFilledButton
import com.twain.interprep.presentation.ui.components.generic.IPHeader
import com.twain.interprep.presentation.ui.components.generic.IPTextInput
import com.twain.interprep.presentation.ui.theme.InterPrepTheme
import com.twain.interprep.presentation.ui.theme.MaterialColorPalette
import com.twain.interprep.presentation.ui.theme.SetStatusBarColor

@Composable
fun GreetingScreen(
    navController: NavHostController
) {
    InterPrepTheme {
        SetStatusBarColor(statusBarColor = MaterialColorPalette.surface)
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialColorPalette.surface,
            content = { padding ->
                GreetingScreenContent(
                    padding = padding,
                    navController = navController
                )
            }
        )
    }
}

@Composable
private fun GreetingScreenContent(
    padding: PaddingValues,
    navController: NavHostController,
    localizationViewModel: LocalizationViewModel = hiltViewModel(),
    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {
    var username by rememberSaveable { mutableStateOf("") }
    var language by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = dimensionResource(id = R.dimen.dimension_16dp),
                vertical = dimensionResource(id = R.dimen.dimension_24dp)
            )

    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dimension_32dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_greeting),
                contentDescription = "Greeting Screen Image"
            )
            IPHeader(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.header_greeting_screen),
                textStyle = typography.bodyLarge,
                alignTextToCenter = false
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.dimension_8dp)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimension_24dp)),
                horizontalAlignment = Alignment.Start
            ) {
                IPTextInput(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textInputAttributes =
                    TextInputAttributes(
                        labelTextId = R.string.label_setting_name,
                        inputType = TextInputType.TEXT,
                        imeAction = ImeAction.Done,
                        singleLine = true
                    ),
                    inputText = username,
                    onTextUpdate = { text ->
                        username = text
                    }
                )
                IPTextInput(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textInputAttributes =
                    TextInputAttributes(
                        labelTextId = R.string.label_setting_language,
                        inputType = TextInputType.DROPDOWN,
                        imeAction = ImeAction.Done,
                        singleLine = true
                    ),
                    inputText = language,
                    onTextUpdate = { text ->
                        language = text
                    }
                )
            }
        }
        IPFilledButton(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.BottomEnd)
                .padding(horizontal = dimensionResource(id = R.dimen.dimension_16dp)),
            text = stringResource(id = R.string.button_next),
            textStyle = typography.bodyLarge,
            onClick = {
                val langCode = localizationViewModel.getLanguageCode(language)
                onboardingViewModel.setUsername(username)
                onboardingViewModel.setLanguage(language, langCode)
                onboardingViewModel.setOnboardingStatus(status = true)
                localizationViewModel.setLocale(context, langCode, false)
                navController.navigate(AppScreens.MainScreens.route) {
                    popUpTo(AppScreens.OnboardingScreens.Greetings.route) { inclusive = true }
                }
            }
        )
    }
}
