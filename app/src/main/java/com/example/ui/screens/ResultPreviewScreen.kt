package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Splitscreen
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CompareMode
import com.example.model.HandwritingStyle
import com.example.model.HandwritingStylesList
import com.example.model.SampleParagraphText
import com.example.ui.components.NotebookPaperView
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

@Composable
fun ResultPreviewScreen(
    currentStyle: HandwritingStyle = HandwritingStylesList[2], // Cursive
    onRegenerateClick: () -> Unit = {},
    onTryAnotherStyleClick: () -> Unit = {},
    onContinueToDownloadClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var compareMode by remember { mutableStateOf(CompareMode.SIDE_BY_SIDE) }
    var sliderSplitPosition by remember { mutableFloatStateOf(0.5f) }

    val originalStyle = remember { HandwritingStylesList[0] } // Raw Original Style

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkNavyBg)
            .testTag("result_preview_column"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Heading & Style Badge
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Your New Handwriting",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F2C52))
                            .border(1.dp, CyanNeon.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = currentStyle.name.uppercase(),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyanNeon
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Identical student paragraph generated with new stroke metrics",
                    fontSize = 11.5.sp,
                    color = TextMuted
                )
            }
        }

        // Compare Mode Switcher Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(DarkNavyCard)
                    .border(1.dp, DarkNavyCardBorder, RoundedCornerShape(12.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CompareModeTab(
                    title = "Side by Side",
                    icon = Icons.Default.Splitscreen,
                    isSelected = compareMode == CompareMode.SIDE_BY_SIDE,
                    onClick = { compareMode = CompareMode.SIDE_BY_SIDE },
                    modifier = Modifier.weight(1f)
                )
                CompareModeTab(
                    title = "Split Slider",
                    icon = Icons.Default.Compare,
                    isSelected = compareMode == CompareMode.INTERACTIVE_SLIDER,
                    onClick = { compareMode = CompareMode.INTERACTIVE_SLIDER },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Display the Handwriting Previews based on Compare Mode
        item {
            if (compareMode == CompareMode.SIDE_BY_SIDE) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Preview 1: Original Handwriting Preview
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.dp, DarkNavyCardBorder, RoundedCornerShape(14.dp))
                            .testTag("preview_original_card"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkNavyCard)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFE11D48))
                                    )
                                    Text(
                                        text = "ORIGINAL NOTEBOOK HANDWRITING",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp,
                                        color = TextLight
                                    )
                                }
                                Text(
                                    text = "Raw Scan",
                                    fontSize = 9.sp,
                                    color = TextDim
                                )
                            }

                            // Render Original Handwriting on Paper
                            NotebookPaperView(
                                text = SampleParagraphText,
                                style = originalStyle,
                                fontSize = 11.5.sp,
                                lineSpacingDp = 20.dp,
                                showSpiralHoles = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(115.dp)
                            )
                        }
                    }

                    // Preview 2: Transformed Handwriting Preview
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.5.dp, CyanNeon.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                            .shadow(8.dp, RoundedCornerShape(14.dp), spotColor = CyanNeon.copy(alpha = 0.3f))
                            .testTag("preview_transformed_card"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = DarkNavyCardElevated)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = CyanNeon,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = "TRANSFORMED: ${currentStyle.name.uppercase()}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp,
                                        color = CyanBright
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(CyanNeon.copy(alpha = 0.15f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "SYNTHESIZED",
                                        fontSize = 8.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CyanNeon
                                    )
                                }
                            }

                            // Render Transformed Handwriting on Paper
                            NotebookPaperView(
                                text = SampleParagraphText,
                                style = currentStyle,
                                fontSize = 12.sp,
                                lineSpacingDp = 20.dp,
                                showSpiralHoles = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(115.dp)
                            )
                        }
                    }
                }
            } else {
                // Interactive Split Slider View
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.5.dp, CyanNeon.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                        .testTag("interactive_split_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkNavyCard)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Original (${(100 - sliderSplitPosition * 100).toInt()}%)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted
                            )
                            Text(
                                text = "${currentStyle.name} (${(sliderSplitPosition * 100).toInt()}%)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyanNeon
                            )
                        }

                        // Display active morphed representation
                        val activeStyleToDisplay = if (sliderSplitPosition > 0.5f) currentStyle else originalStyle
                        NotebookPaperView(
                            text = SampleParagraphText,
                            style = activeStyleToDisplay,
                            fontSize = 12.sp,
                            lineSpacingDp = 21.dp,
                            showSpiralHoles = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(210.dp)
                        )

                        Slider(
                            value = sliderSplitPosition,
                            onValueChange = { sliderSplitPosition = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = SliderDefaults.colors(
                                thumbColor = CyanNeon,
                                activeTrackColor = CyanNeon,
                                inactiveTrackColor = Color(0xFF1E2D48)
                            )
                        )
                    }
                }
            }
        }

        // Secondary Action Buttons: Compare, Regenerate, Try Another Style
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Compare Button
                OutlinedButton(
                    onClick = {
                        compareMode = if (compareMode == CompareMode.SIDE_BY_SIDE) {
                            CompareMode.INTERACTIVE_SLIDER
                        } else {
                            CompareMode.SIDE_BY_SIDE
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("btn_compare_toggle"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkNavyCardElevated),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(CyanNeon.copy(alpha = 0.5f), ElectricBlue.copy(alpha = 0.3f)))
                    ),
                    contentPadding = PaddingValues(horizontal = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Compare,
                        contentDescription = "Compare",
                        tint = CyanNeon,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "Compare",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite
                    )
                }

                // Regenerate Button
                OutlinedButton(
                    onClick = onRegenerateClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("btn_regenerate"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkNavyCardElevated),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(CyanNeon.copy(alpha = 0.5f), ElectricBlue.copy(alpha = 0.3f)))
                    ),
                    contentPadding = PaddingValues(horizontal = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Regenerate",
                        tint = CyanNeon,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "Regenerate",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite
                    )
                }

                // Try Another Style Button
                OutlinedButton(
                    onClick = onTryAnotherStyleClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("btn_try_another_style"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkNavyCardElevated),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(listOf(CyanNeon.copy(alpha = 0.5f), ElectricBlue.copy(alpha = 0.3f)))
                    ),
                    contentPadding = PaddingValues(horizontal = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Palette,
                        contentDescription = "Styles",
                        tint = CyanNeon,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "Styles",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite
                    )
                }
            }
        }

        // Final Confirm Button: Move to Download Screen
        item {
            Spacer(modifier = Modifier.height(2.dp))
            Button(
                onClick = onContinueToDownloadClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .shadow(8.dp, RoundedCornerShape(14.dp), spotColor = CyanNeon.copy(alpha = 0.5f))
                    .testTag("btn_proceed_to_download"),
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
                            text = "Accept & Proceed to Download",
                            color = Color(0xFF04101E),
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
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
fun CompareModeTab(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) CyanNeon else Color.Transparent)
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) Color(0xFF04101E) else TextMuted,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = title,
                fontSize = 11.5.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color(0xFF04101E) else TextLight
            )
        }
    }
}
