package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppScreen
import com.example.model.HandwritingStyle
import com.example.model.HandwritingStylesList
import com.example.ui.components.PhoneMockupFrame
import com.example.ui.theme.CyanBright
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.DarkNavyCardBorder
import com.example.ui.theme.DarkNavyCardElevated
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.TextDim
import com.example.ui.theme.TextLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

/**
 * Landscape 16:9 UI Contact Sheet showcasing all 5 smartphone screens side-by-side
 * with realistic Android screen proportions, consistent dark navy & electric cyan styling,
 * and high-fidelity mockups.
 */
@Composable
fun ContactSheetScreen(
    currentSelectedStyle: HandwritingStyle = HandwritingStylesList[2],
    activeFocusedScreen: AppScreen = AppScreen.HOME,
    onScreenSelected: (AppScreen) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val horizontalScrollState = rememberScrollState()

    // Phone mockup dimension scaling
    var scaleTier by remember { mutableFloatStateOf(1.0f) }
    val phoneWidth: Dp = (290 * scaleTier).dp
    val phoneHeight: Dp = (590 * scaleTier).dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkNavyBg)
            .testTag("contact_sheet_container")
    ) {
        // Presentation Banner Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF090E1B))
                .border(1.dp, Color(0xFF16233B))
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Brand & Concept
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.handmorph_icon),
                    contentDescription = "HandMorph AI",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, CyanNeon.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                )
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "HANDMORPH AI",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextWhite,
                            letterSpacing = 1.sp
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(CyanNeon.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "5-SCREEN CONTACT SHEET",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyanNeon
                            )
                        }
                    }
                    Text(
                        text = "One Handwriting. Multiple Styles. · Material 3 Mobile Specification",
                        fontSize = 10.5.sp,
                        color = TextMuted
                    )
                }
            }

            // Specs & Zoom Controls
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Zoom toggles
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkNavyCard)
                        .border(1.dp, DarkNavyCardBorder, RoundedCornerShape(8.dp))
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ZoomOut,
                        contentDescription = "Compact",
                        tint = if (scaleTier < 1.0f) CyanNeon else TextDim,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { scaleTier = 0.85f }
                    )
                    Text(
                        text = "${(scaleTier * 100).toInt()}%",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextLight
                    )
                    Icon(
                        imageVector = Icons.Default.ZoomIn,
                        contentDescription = "Zoom In",
                        tint = if (scaleTier > 1.0f) CyanNeon else TextDim,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { scaleTier = 1.05f }
                    )
                }

                // Interactive Mode prompt
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF0F2648))
                        .border(1.dp, CyanNeon.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Smartphone,
                        contentDescription = null,
                        tint = CyanNeon,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "Tap any phone to test live",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CyanBright
                    )
                }
            }
        }

        // Horizontal Panoramic 5-Screen Runway
        Row(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(horizontalScrollState)
                .padding(horizontal = 24.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ==========================================
            // SCREEN 1: HOME SCREEN
            // ==========================================
            ScreenPresentationColumn(
                screenNumber = "SCREEN 01",
                screenTitle = "HOME SCREEN",
                tag = "LAUNCHPAD & INTAKE",
                accentColor = CyanNeon,
                isSelected = activeFocusedScreen == AppScreen.HOME,
                phoneWidth = phoneWidth,
                onHeaderClick = { onScreenSelected(AppScreen.HOME) }
            ) {
                PhoneMockupFrame(
                    screen = AppScreen.HOME,
                    targetWidth = phoneWidth,
                    targetHeight = phoneHeight,
                    isSelected = activeFocusedScreen == AppScreen.HOME,
                    onClick = { onScreenSelected(AppScreen.HOME) }
                ) {
                    HomeScreen(
                        onUploadClick = { onScreenSelected(AppScreen.ANALYSIS) },
                        onTakePhotoClick = { onScreenSelected(AppScreen.ANALYSIS) },
                        onProjectClick = { onScreenSelected(AppScreen.RESULT_PREVIEW) }
                    )
                }
            }

            // ==========================================
            // SCREEN 2: HANDWRITING ANALYSIS
            // ==========================================
            ScreenPresentationColumn(
                screenNumber = "SCREEN 02",
                screenTitle = "HANDWRITING ANALYSIS",
                tag = "NEURAL VISION SCAN",
                accentColor = ElectricBlue,
                isSelected = activeFocusedScreen == AppScreen.ANALYSIS,
                phoneWidth = phoneWidth,
                onHeaderClick = { onScreenSelected(AppScreen.ANALYSIS) }
            ) {
                PhoneMockupFrame(
                    screen = AppScreen.ANALYSIS,
                    targetWidth = phoneWidth,
                    targetHeight = phoneHeight,
                    isSelected = activeFocusedScreen == AppScreen.ANALYSIS,
                    onClick = { onScreenSelected(AppScreen.ANALYSIS) }
                ) {
                    AnalysisScreen(
                        onContinueClick = { onScreenSelected(AppScreen.STYLE_SELECTION) },
                        progress = 0.88f
                    )
                }
            }

            // ==========================================
            // SCREEN 3: HANDWRITING STYLE SELECTION
            // ==========================================
            ScreenPresentationColumn(
                screenNumber = "SCREEN 03",
                screenTitle = "STYLE SELECTION",
                tag = "7 PERSONAL PRESETS",
                accentColor = CyanBright,
                isSelected = activeFocusedScreen == AppScreen.STYLE_SELECTION,
                phoneWidth = phoneWidth,
                onHeaderClick = { onScreenSelected(AppScreen.STYLE_SELECTION) }
            ) {
                PhoneMockupFrame(
                    screen = AppScreen.STYLE_SELECTION,
                    targetWidth = phoneWidth,
                    targetHeight = phoneHeight,
                    isSelected = activeFocusedScreen == AppScreen.STYLE_SELECTION,
                    onClick = { onScreenSelected(AppScreen.STYLE_SELECTION) }
                ) {
                    StyleSelectionScreen(
                        selectedStyle = currentSelectedStyle,
                        onProceedClick = { onScreenSelected(AppScreen.RESULT_PREVIEW) }
                    )
                }
            }

            // ==========================================
            // SCREEN 4: RESULT PREVIEW
            // ==========================================
            ScreenPresentationColumn(
                screenNumber = "SCREEN 04",
                screenTitle = "RESULT PREVIEW",
                tag = "BEFORE & AFTER COMPARE",
                accentColor = CyanNeon,
                isSelected = activeFocusedScreen == AppScreen.RESULT_PREVIEW,
                phoneWidth = phoneWidth,
                onHeaderClick = { onScreenSelected(AppScreen.RESULT_PREVIEW) }
            ) {
                PhoneMockupFrame(
                    screen = AppScreen.RESULT_PREVIEW,
                    targetWidth = phoneWidth,
                    targetHeight = phoneHeight,
                    isSelected = activeFocusedScreen == AppScreen.RESULT_PREVIEW,
                    onClick = { onScreenSelected(AppScreen.RESULT_PREVIEW) }
                ) {
                    ResultPreviewScreen(
                        currentStyle = currentSelectedStyle,
                        onContinueToDownloadClick = { onScreenSelected(AppScreen.DOWNLOAD) },
                        onTryAnotherStyleClick = { onScreenSelected(AppScreen.STYLE_SELECTION) }
                    )
                }
            }

            // ==========================================
            // SCREEN 5: DOWNLOAD SCREEN
            // ==========================================
            ScreenPresentationColumn(
                screenNumber = "SCREEN 05",
                screenTitle = "DOWNLOAD SCREEN",
                tag = "EXPORT & PRINT READY",
                accentColor = ElectricBlue,
                isSelected = activeFocusedScreen == AppScreen.DOWNLOAD,
                phoneWidth = phoneWidth,
                onHeaderClick = { onScreenSelected(AppScreen.DOWNLOAD) }
            ) {
                PhoneMockupFrame(
                    screen = AppScreen.DOWNLOAD,
                    targetWidth = phoneWidth,
                    targetHeight = phoneHeight,
                    isSelected = activeFocusedScreen == AppScreen.DOWNLOAD,
                    onClick = { onScreenSelected(AppScreen.DOWNLOAD) }
                ) {
                    DownloadScreen(
                        currentStyle = currentSelectedStyle
                    )
                }
            }
        }
    }
}

@Composable
private fun ScreenPresentationColumn(
    screenNumber: String,
    screenTitle: String,
    tag: String,
    accentColor: Color,
    isSelected: Boolean,
    phoneWidth: Dp,
    onHeaderClick: () -> Unit,
    phoneContent: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(phoneWidth)
    ) {
        // Top Screen Identification Pill
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(if (isSelected) Color(0xFF14294E) else DarkNavyCard)
                .border(
                    1.dp,
                    if (isSelected) CyanNeon else DarkNavyCardBorder,
                    RoundedCornerShape(12.dp)
                )
                .clickable { onHeaderClick() }
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(accentColor)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$screenNumber : $screenTitle",
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) CyanBright else TextWhite,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = tag,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDim,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Phone Frame
        phoneContent()

        Spacer(modifier = Modifier.height(10.dp))

        // Architectural Details Chip
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF0C1324))
                .border(0.75.dp, Color(0xFF1C2B47), RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.OpenInFull,
                contentDescription = null,
                tint = CyanBright,
                modifier = Modifier.size(11.dp)
            )
            Text(
                text = "Interactive Screen View",
                fontSize = 9.sp,
                color = TextLight,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
