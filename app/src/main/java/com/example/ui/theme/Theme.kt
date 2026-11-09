package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val HandMorphColorScheme = darkColorScheme(
    primary = CyanNeon,
    onPrimary = Color(0xFF04101E),
    primaryContainer = Color(0xFF0C274D),
    onPrimaryContainer = CyanBright,
    secondary = ElectricBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF132A66),
    onSecondaryContainer = Color(0xFFD6E3FF),
    tertiary = CyanBright,
    onTertiary = Color(0xFF04101E),
    background = DarkNavyBg,
    onBackground = TextWhite,
    surface = DarkNavySurface,
    onSurface = TextWhite,
    surfaceVariant = DarkNavyCard,
    onSurfaceVariant = TextLight,
    outline = DarkNavyCardBorder,
    outlineVariant = Color(0xFF1E355B)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false, // Enforce our custom premium dark navy theme
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = HandMorphColorScheme,
        typography = Typography,
        content = content
    )
}
