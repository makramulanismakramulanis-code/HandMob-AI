package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Premium Dark Navy Theme
val DarkNavyBg = Color(0xFF070B14)
val DarkNavySurface = Color(0xFF0D1424)
val DarkNavyCard = Color(0xFF141F36)
val DarkNavyCardBorder = Color(0xFF1E2F52)
val DarkNavyCardElevated = Color(0xFF1A2845)

// Electric Cyan & Blue Accents
val CyanNeon = Color(0xFF00E5FF)
val CyanBright = Color(0xFF00F2FE)
val ElectricBlue = Color(0xFF2979FF)
val DeepElectricBlue = Color(0xFF1E50D0)
val ElectricViolet = Color(0xFF7C3AED)
val AmberGlow = Color(0xFFFFB703)
val SuccessGreen = Color(0xFF10B981)

// Typography & Neutrals
val TextWhite = Color(0xFFFFFFFF)
val TextLight = Color(0xFFE2E8F0)
val TextMuted = Color(0xFF94A3B8)
val TextDim = Color(0xFF64748B)

// Gradients
val CyanBlueGradient = Brush.horizontalGradient(
    colors = listOf(CyanNeon, ElectricBlue)
)

val CyanBlueDiagonalGradient = Brush.linearGradient(
    colors = listOf(CyanBright, ElectricBlue)
)

val CardGlowBorder = Brush.linearGradient(
    colors = listOf(CyanNeon.copy(alpha = 0.5f), ElectricBlue.copy(alpha = 0.1f))
)

val ScanLaserGradient = Brush.verticalGradient(
    colors = listOf(
        CyanNeon.copy(alpha = 0f),
        CyanNeon.copy(alpha = 0.85f),
        Color.White,
        CyanNeon.copy(alpha = 0.85f),
        CyanNeon.copy(alpha = 0f)
    )
)
