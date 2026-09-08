package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CyberColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = CyberBackgroundDarker,
    primaryContainer = NeonCyanDark,
    onPrimaryContainer = TextWhite,
    secondary = EmeraldGreen,
    onSecondary = CyberBackgroundDarker,
    secondaryContainer = EmeraldGreenDark,
    onSecondaryContainer = TextWhite,
    tertiary = CyberPurple,
    tertiaryContainer = CyberPurpleGlow,
    onTertiaryContainer = TextWhite,
    error = CrimsonRed,
    onError = TextWhite,
    errorContainer = CrimsonRedDark,
    background = CyberBackground,
    onBackground = TextWhite,
    surface = CyberSurface,
    onSurface = TextWhite,
    surfaceVariant = CyberSurfaceVariant,
    onSurfaceVariant = TextMuted,
    outline = CyberBorder,
    outlineVariant = CyberBorderBright
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit,
) {
    // Tech-Minimalist Dark Mode is always enabled
    MaterialTheme(
        colorScheme = CyberColorScheme,
        typography = Typography,
        content = content
    )
}
