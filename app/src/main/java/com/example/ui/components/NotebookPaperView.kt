package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.HandwritingStyle

/**
 * Authentic ruled notebook paper component with blue horizontal lines,
 * red vertical margin, spiral perforations, and styled handwriting ink.
 */
@Composable
fun NotebookPaperView(
    text: String,
    style: HandwritingStyle,
    modifier: Modifier = Modifier,
    lineSpacingDp: Dp = 26.dp,
    fontSize: TextUnit = 13.sp,
    showSpiralHoles: Boolean = true,
    paperColor: Color = Color(0xFFFAF9F6)
) {
    val paperShape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .clip(paperShape)
            .shadow(4.dp, paperShape)
            .background(paperColor)
            .border(1.dp, Color(0xFFE2E8F0), paperShape)
    ) {
        // Draw the notebook ruling lines and margin line
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val lineSpacingPx = lineSpacingDp.toPx()

            // Vertical margin line (faint red)
            val marginX = if (showSpiralHoles) 34.dp.toPx() else 24.dp.toPx()
            drawLine(
                color = Color(0x66EF4444), // Faint red margin
                start = Offset(marginX, 0f),
                end = Offset(marginX, canvasHeight),
                strokeWidth = 1.2f
            )

            // Horizontal faint blue notebook lines
            var currentY = 32.dp.toPx()
            while (currentY < canvasHeight) {
                drawLine(
                    color = Color(0x333B82F6), // Soft light blue ruled line
                    start = Offset(0f, currentY),
                    end = Offset(canvasWidth, currentY),
                    strokeWidth = 1.0f
                )
                currentY += lineSpacingPx
            }
        }

        // Left spiral punch holes (optional)
        if (showSpiralHoles) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(8) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFCBD5E1))
                            .border(0.5.dp, Color(0xFF94A3B8), CircleShape)
                    )
                }
            }
        }

        // Handwriting Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = if (showSpiralHoles) 42.dp else 30.dp,
                    end = 16.dp,
                    top = 18.dp,
                    bottom = 16.dp
                )
        ) {
            Text(
                text = text,
                color = Color(style.inkHex),
                fontSize = fontSize,
                fontFamily = style.fontFamily,
                fontStyle = style.fontStyle,
                fontWeight = style.fontWeight,
                lineHeight = (fontSize.value * 1.85).sp,
                letterSpacing = style.letterSpacingSp.sp,
                modifier = Modifier.graphicsLayer {
                    // Subtle dynamic slant transformation
                    if (style.slantDegree != 0f) {
                        rotationZ = (style.slantDegree * 0.15f)
                    }
                }
            )
        }
    }
}
