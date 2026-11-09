package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.NetworkCell
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppScreen
import com.example.ui.theme.CyanBright
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DarkNavyCardBorder
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

/**
 * High-fidelity Android Smartphone frame mockup with realistic hardware proportions,
 * punch-hole camera, status bar indicators, and home gesture pill.
 */
@Composable
fun PhoneMockupFrame(
    screen: AppScreen,
    modifier: Modifier = Modifier,
    targetWidth: Dp = 330.dp,
    targetHeight: Dp = 680.dp,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val chassisShape = RoundedCornerShape(40.dp)
    val screenShape = RoundedCornerShape(30.dp)

    val borderGlow = if (isSelected) {
        Brush.linearGradient(listOf(CyanNeon, ElectricBlue, CyanBright))
    } else {
        Brush.linearGradient(
            listOf(
                Color(0xFF2A3C63),
                Color(0xFF131D33),
                Color(0xFF233252)
            )
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Outer Smartphone Hardware Chassis
        Surface(
            modifier = Modifier
                .width(targetWidth)
                .height(targetHeight)
                .shadow(
                    elevation = if (isSelected) 24.dp else 12.dp,
                    shape = chassisShape,
                    ambientColor = if (isSelected) CyanNeon.copy(alpha = 0.4f) else Color.Black,
                    spotColor = if (isSelected) ElectricBlue.copy(alpha = 0.6f) else Color(0xFF0F1A2F)
                )
                .border(
                    width = if (isSelected) 2.5.dp else 2.dp,
                    brush = borderGlow,
                    shape = chassisShape
                )
                .then(
                    if (onClick != null) Modifier.clickable { onClick() } else Modifier
                ),
            shape = chassisShape,
            color = Color(0xFF0A0F1D) // Dark metallic titanium rim
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(7.dp) // Outer hardware bezel
            ) {
                // The Inner Display Screen
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(screenShape)
                        .background(DarkNavyBg)
                ) {
                    // Screen Main Content
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 34.dp, bottom = 22.dp)
                    ) {
                        content()
                    }

                    // Realistic Android Status Bar
                    PhoneStatusBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .height(34.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color(0xCC070B14),
                                        Color(0x66070B14),
                                        Color.Transparent
                                    )
                                )
                            )
                            .padding(horizontal = 18.dp)
                    )

                    // Bottom Android Navigation Gesture Pill
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(22.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        Color(0x99070B14)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .width(90.dp)
                                .height(4.5.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color.White.copy(alpha = 0.6f))
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PhoneStatusBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Clock
        Text(
            text = "9:41",
            color = TextWhite,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.2.sp
        )

        // Center Punch-hole Camera
        Box(
            modifier = Modifier
                .size(11.dp)
                .clip(CircleShape)
                .background(Color(0xFF05070D))
                .border(0.75.dp, Color(0xFF1E293B), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF0D1B2A))
            )
        }

        // Status Icons
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Wifi,
                contentDescription = "WiFi",
                tint = TextWhite.copy(alpha = 0.9f),
                modifier = Modifier.size(12.dp)
            )
            Icon(
                imageVector = Icons.Default.NetworkCell,
                contentDescription = "Cellular",
                tint = TextWhite.copy(alpha = 0.9f),
                modifier = Modifier.size(12.dp)
            )
            Icon(
                imageVector = Icons.Default.BatteryFull,
                contentDescription = "Battery",
                tint = CyanBright,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}
