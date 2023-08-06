package com.twain.interprep.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


@Composable
fun InterPrepTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // TODO: Use dynamicColor value & InterPrepColorScheme() to support Material 3
    InterPrepColorPalette(darkTheme, content)
}

@Composable
private fun InterPrepColorPalette(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val ipColorPalette =
        if (darkTheme) DarkColorPalette
        else LightColorPalette

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = ipColorPalette.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalIPColorPalette provides ipColorPalette
    ) {
        MaterialTheme(
//            colorScheme = colorScheme,
//            typography = Typography,
            content = content
        )
    }
}

@Composable
private fun InterPrepColorScheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    // TODO: Currently dynamicColorScheme doesn't support customisation of colors. Find an alternative
    val context = LocalContext.current
    val colorScheme =
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

}
