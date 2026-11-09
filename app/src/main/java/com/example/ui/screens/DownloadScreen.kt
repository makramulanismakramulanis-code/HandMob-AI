package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDim
import com.example.ui.theme.TextLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun DownloadScreen(
    currentStyle: HandwritingStyle = HandwritingStylesList[2],
    onDownloadPdfClick: () -> Unit = {},
    onDownloadPngClick: () -> Unit = {},
    onSaveToMyStylesClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkNavyBg)
            .testTag("download_screen_column"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Heading & Success Badge
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Your Handwriting Is Ready",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0D3328))
                            .border(1.dp, SuccessGreen.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "READY",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "High-definition 300 DPI vector pages formatted for export",
                    fontSize = 11.5.sp,
                    color = TextMuted
                )
            }
        }

        // Large Notebook Page Preview
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .border(1.5.dp, CyanNeon.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
                    .shadow(12.dp, RoundedCornerShape(18.dp), spotColor = CyanNeon.copy(alpha = 0.25f))
                    .testTag("large_notebook_page_preview"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkNavyCard)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Page metadata header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "PAGE 1 OF 1",
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyanBright
                            )
                            Text(text = "•", fontSize = 9.5.sp, color = TextDim)
                            Text(
                                text = currentStyle.name,
                                fontSize = 9.5.sp,
                                color = TextMuted
                            )
                        }
                        Text(
                            text = "300 DPI VECTOR",
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            color = TextLight
                        )
                    }

                    // Authentic full page preview
                    NotebookPaperView(
                        text = "$SampleParagraphText\n\nEnergy transfer throughout these biochemical pathways illustrates how foundational thermodynamics governs microscopic life. Every step balances entropy and efficiency.",
                        style = currentStyle,
                        fontSize = 11.5.sp,
                        lineSpacingDp = 20.dp,
                        showSpiralHoles = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    // Page footer indicator
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "HandMorph AI Engine · Certified Style Synthesis",
                            fontSize = 8.5.sp,
                            color = TextDim
                        )
                        Text(
                            text = "342 Words",
                            fontSize = 8.5.sp,
                            color = CyanNeon
                        )
                    }
                }
            }
        }

        // Export Action Buttons: PDF & PNG
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Download PDF Button (Primary Gradient)
                Button(
                    onClick = onDownloadPdfClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .shadow(8.dp, RoundedCornerShape(12.dp), spotColor = CyanNeon.copy(alpha = 0.5f))
                        .testTag("btn_download_pdf"),
                    shape = RoundedCornerShape(12.dp),
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
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PictureAsPdf,
                                contentDescription = "PDF",
                                tint = Color(0xFF04101E),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Download PDF",
                                color = Color(0xFF04101E),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Download PNG Button
                OutlinedButton(
                    onClick = onDownloadPngClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("btn_download_png"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkNavyCardElevated),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.horizontalGradient(
                            listOf(CyanNeon.copy(alpha = 0.6f), ElectricBlue.copy(alpha = 0.4f))
                        )
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "PNG",
                            tint = CyanNeon,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Download PNG",
                            color = TextWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Secondary Action Buttons: Save to My Styles & Share
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Save to My Styles Button
                OutlinedButton(
                    onClick = onSaveToMyStylesClick,
                    modifier = Modifier
                        .weight(1.1f)
                        .height(46.dp)
                        .testTag("btn_save_my_styles"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkNavyCard),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(
                            listOf(DarkNavyCardBorder, Color(0xFF2A3D66))
                        )
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Bookmark",
                            tint = CyanBright,
                            modifier = Modifier.size(17.dp)
                        )
                        Text(
                            text = "Save to My Styles",
                            color = TextLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Share Button
                OutlinedButton(
                    onClick = onShareClick,
                    modifier = Modifier
                        .weight(0.9f)
                        .height(46.dp)
                        .testTag("btn_share"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = DarkNavyCard),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(
                            listOf(DarkNavyCardBorder, Color(0xFF2A3D66))
                        )
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = CyanNeon,
                            modifier = Modifier.size(17.dp)
                        )
                        Text(
                            text = "Share",
                            color = TextLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
