package com.example.parcialtp3.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MainGreen,
    secondary = MainGreen,
    tertiary = BackgroundDarkModeAndLetters,
    background = DarkModeGreenBlack,
    surface = BackgroundDarkModeAndLetters,
    onPrimary = BackgroundGreenWhiteAndLetters,
    onSecondary = BackgroundGreenWhiteAndLetters,
    onTertiary = MainGreen,
    onBackground = BackgroundGreenWhiteAndLetters,
    onSurface = BackgroundGreenWhiteAndLetters
)

private val LightColorScheme = lightColorScheme(
    primary = MainGreen,
    secondary = MainGreen,
    tertiary = BackgroundGreenWhiteAndLetters,
    background = LightGreen,
    surface = BackgroundGreenWhiteAndLetters
)

@Composable
fun ParcialTP3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,  // Desactivar colores dinámicos para usar colores de FinWise
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}