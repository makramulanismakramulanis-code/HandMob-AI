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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
fun StyleSelectionScreen(
    selectedStyle: HandwritingStyle = HandwritingStylesList[2], // Default to Cursive or Neat
    onSelectStyle: (HandwritingStyle) -> Unit = {},
    onProceedClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkNavyBg)
            .testTag("style_selection_column"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Heading & Description
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Choose Your Handwriting Style",
                        fontSize = 17.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Presets",
                        tint = CyanNeon,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "7 distinct styles recreated from your personal letter dynamics",
                    fontSize = 11.5.sp,
                    color = TextMuted
                )
            }
        }

        // All 7 Styles
        items(HandwritingStylesList) { style ->
            val isSelected = style.id == selectedStyle.id

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        brush = if (isSelected) {
                            Brush.horizontalGradient(listOf(CyanNeon, ElectricBlue))
                        } else {
                            Brush.linearGradient(listOf(DarkNavyCardBorder, DarkNavyCardBorder))
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onSelectStyle(style) }
                    .shadow(
                        elevation = if (isSelected) 8.dp else 2.dp,
                        shape = RoundedCornerShape(16.dp),
                        spotColor = if (isSelected) CyanNeon.copy(alpha = 0.4f) else Color.Transparent
                    )
                    .testTag("style_card_${style.id}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) DarkNavyCardElevated else DarkNavyCard
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Header row: Selection radio + Name + Badge
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Radio circle
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected) CyanNeon else Color(0xFF131D33)
                                    )
                                    .border(
                                        1.dp,
                                        if (isSelected) CyanNeon else Color(0xFF2A3D63),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = Color(0xFF04101E),
                                        modifier = Modifier.size(13.dp)
                                    )
                                }
                            }

                            Column {
                                Text(
                                    text = style.name,
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) CyanBright else TextWhite
                                )
                                Text(
                                    text = style.description,
                                    fontSize = 10.5.sp,
                                    color = TextMuted
                                )
                            }
                        }

                        if (style.badge != null) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        if (isSelected) CyanNeon.copy(alpha = 0.2f) else Color(0xFF111E35)
                                    )
                                    .border(
                                        0.75.dp,
                                        if (isSelected) CyanNeon.copy(alpha = 0.6f) else Color(0xFF1E3255),
                                        RoundedCornerShape(6.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.5.dp)
                            ) {
                                Text(
                                    text = style.badge,
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) CyanNeon else TextLight
                                )
                            }
                        }
                    }

                    // Live Handwriting Preview on Ruled Notebook Paper
                    NotebookPaperView(
                        text = style.sampleSnippet,
                        style = style,
                        fontSize = 12.sp,
                        lineSpacingDp = 22.dp,
                        showSpiralHoles = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                    )
                }
            }
        }

        // Select Style Action Button
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Button(
                onClick = onProceedClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .shadow(8.dp, RoundedCornerShape(14.dp), spotColor = CyanNeon.copy(alpha = 0.5f))
                    .testTag("select_style_confirm_button"),
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
                            text = "Select ${selectedStyle.name}",
                            color = Color(0xFF04101E),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Select",
                            tint = Color(0xFF04101E),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
