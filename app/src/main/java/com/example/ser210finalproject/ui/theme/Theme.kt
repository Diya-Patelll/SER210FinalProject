package com.example.ser210finalproject.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = QuinnipiacGold,
    onPrimary = QuinnipiacNavy,
    secondary = QuinnipiacBlueLight,
    tertiary = QuinnipiacGoldSoft,
    background = QuinnipiacNavy,
    onBackground = QuinnipiacBackground,
    surface = QuinnipiacBlue,
    onSurface = QuinnipiacBackground
)

private val LightColorScheme = lightColorScheme(
    primary = QuinnipiacBlue,
    onPrimary = QuinnipiacSurface,
    secondary = QuinnipiacGold,
    onSecondary = QuinnipiacNavy,
    tertiary = QuinnipiacBlueLight,
    background = QuinnipiacBackground,
    onBackground = QuinnipiacTextDark,
    surface = QuinnipiacSurface,
    onSurface = QuinnipiacTextDark
)

@Composable
fun SER210FinalProjectTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme && !dynamicColor) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
