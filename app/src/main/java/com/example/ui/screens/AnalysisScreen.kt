package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CyanBright
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.DarkNavyCardBorder
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ScanLaserGradient
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDim
import com.example.ui.theme.TextLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun AnalysisScreen(
    onContinueClick: () -> Unit = {},
    progress: Float = 0.88f,
    modifier: Modifier = Modifier
) {
    // Laser scan animation
    val infiniteTransition = rememberInfiniteTransition(label = "scan_laser")
    val scanOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laser_pos"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkNavyBg)
            .testTag("analysis_screen_column"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Heading & Engine Badge
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Analyzing Your Handwriting",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0C2442))
                            .border(1.dp, CyanNeon.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "${(progress * 100).toInt()}%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyanNeon
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "AI vision models parsing stroke dynamics and letterforms",
                    fontSize = 11.5.sp,
                    color = TextMuted
                )
            }
        }

        // Uploaded Notebook Page Preview with Scanning Animation
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .border(1.5.dp, DarkNavyCardBorder, RoundedCornerShape(18.dp))
                    .shadow(12.dp, RoundedCornerShape(18.dp), spotColor = CyanNeon.copy(alpha = 0.3f))
                    .testTag("notebook_preview_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Uploaded Notebook Page Image
                    Image(
                        painter = painterResource(id = R.drawable.notebook_sample),
                        contentDescription = "Uploaded notebook page preview",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Dark tint overlay for sci-fi HUD vibe
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x33070B14))
                    )

                    // Animated Scanning Laser Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(28.dp)
                            .align(Alignment.TopCenter)
                            .padding(top = (scanOffset * 190).dp)
                            .background(ScanLaserGradient)
                    )

                    // Target Corner Brackets Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        // Top-left bracket
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.TopStart)
                                .border(
                                    width = 2.dp,
                                    color = CyanNeon,
                                    shape = RoundedCornerShape(topStart = 6.dp)
                                )
                        )
                        // Top-right bracket
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.TopEnd)
                                .border(
                                    width = 2.dp,
                                    color = CyanNeon,
                                    shape = RoundedCornerShape(topEnd = 6.dp)
                                )
                        )
                        // Bottom-left bracket
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.BottomStart)
                                .border(
                                    width = 2.dp,
                                    color = CyanNeon,
                                    shape = RoundedCornerShape(bottomStart = 6.dp)
                                )
                        )
                        // Bottom-right bracket
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.BottomEnd)
                                .border(
                                    width = 2.dp,
                                    color = CyanNeon,
                                    shape = RoundedCornerShape(bottomEnd = 6.dp)
                                )
                        )

                        // Live detection tag
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xD9070B14))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(CyanNeon)
                            )
                            Text(
                                text = "RAW NOTEBOOK PAGE #01 · 342 WORDS DETECTED",
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )
                        }
                    }
                }
            }
        }

        // Progress Animation Bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Neural Synthesis Pipeline",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextLight
                    )
                    Text(
                        text = "3 of 3 Tasks",
                        fontSize = 11.sp,
                        color = CyanBright
                    )
                }

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = CyanNeon,
                    trackColor = Color(0xFF131F38)
                )
            }
        }

        // Status List Items
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Status 1: Reading Text
                AnalysisStatusRow(
                    icon = Icons.Default.TextFields,
                    title = "Reading Text",
                    description = "Transcribed 342 words & chemical formulas with 99.4% accuracy",
                    isDone = true,
                    isActive = false
                )

                // Status 2: Analyzing Letter Shapes
                AnalysisStatusRow(
                    icon = Icons.Default.DataUsage,
                    title = "Analyzing Letter Shapes",
                    description = "Measuring baseline slant (14°), ascenders, x-height & loops",
                    isDone = true,
                    isActive = false
                )

                // Status 3: Learning Personal Style
                AnalysisStatusRow(
                    icon = Icons.Default.Psychology,
                    title = "Learning Personal Style",
                    description = "Building neural pen pressure matrix and fluid ligature map",
                    isDone = false,
                    isActive = true
                )
            }
        }

        // Continue Button
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Button(
                onClick = onContinueClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .shadow(8.dp, RoundedCornerShape(14.dp), spotColor = CyanNeon.copy(alpha = 0.5f))
                    .testTag("analysis_continue_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(listOf(CyanNeon, ElectricBlue))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Continue to Style Selection",
                            color = Color(0xFF04101E),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Continue",
                            tint = Color(0xFF04101E),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnalysisStatusRow(
    icon: ImageVector,
    title: String,
    description: String,
    isDone: Boolean,
    isActive: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                1.dp,
                if (isActive) CyanNeon.copy(alpha = 0.5f) else DarkNavyCardBorder,
                RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) Color(0xFF142442) else DarkNavyCard
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Icon Indicator
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (isDone) Color(0xFF0D3328)
                        else if (isActive) Color(0xFF0F325C)
                        else Color(0xFF162035)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isDone) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Done",
                        tint = SuccessGreen,
                        modifier = Modifier.size(20.dp)
                    )
                } else if (isActive) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = CyanNeon
                    )
                } else {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = TextDim,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Description
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) CyanBright else TextWhite
                    )
                    if (isDone) {
                        Text(
                            text = "COMPLETED",
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )
                    } else if (isActive) {
                        Text(
                            text = "SCANNING",
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyanNeon
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = TextMuted
                )
            }
        }
    }
}
