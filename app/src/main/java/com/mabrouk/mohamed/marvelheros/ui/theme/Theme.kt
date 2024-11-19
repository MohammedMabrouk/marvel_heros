package com.mabrouk.mohamed.marvelheros.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = darkPrimaryColor,
    primaryVariant = darkPrimaryColor,
    onPrimary = darkPrimaryColor,
    background = darkBackgroundColor,
    onBackground = darkBackgroundColor,
    error = darkErrorColor,
    onError = darkErrorColor,
    secondary = darkValueColor,
    surface = darkBackgroundColor,
    onSurface = darkBackgroundColor,
)

private val LightColorPalette = lightColors(
    primary = primaryColor,
    primaryVariant = primaryColor,
    onPrimary = primaryColor,
    background = backgroundColor,
    onBackground = backgroundColor,
    error = errorColor,
    onError = errorColor,
    secondary = valueColor,
    surface = backgroundColor,
    onSurface = backgroundColor,
)

@Composable
fun MarvelHerosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        content = content,
    )
}