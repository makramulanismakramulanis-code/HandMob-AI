package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.AppScreen
import com.example.model.DisplayMode
import com.example.ui.screens.AnalysisScreen
import com.example.ui.screens.ContactSheetScreen
import com.example.ui.screens.DownloadScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ResultPreviewScreen
import com.example.ui.screens.StyleSelectionScreen
import com.example.ui.theme.CyanBright
import com.example.ui.theme.CyanNeon
import com.example.ui.theme.DarkNavyBg
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.DarkNavyCardBorder
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.TextDim
import com.example.ui.theme.TextLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.viewmodel.HandMorphViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                HandMorphApp()
            }
        }
    }
}

@Composable
fun HandMorphApp(viewModel: HandMorphViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Intercept back button
    BackHandler(enabled = true) {
        val handled = viewModel.navigateBack()
        if (!handled && uiState.displayMode == DisplayMode.INTERACTIVE_FLOW) {
            viewModel.setDisplayMode(DisplayMode.CONTACT_SHEET)
        }
    }

    LaunchedEffect(uiState.feedbackMessage) {
        uiState.feedbackMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.dismissFeedback()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavyBg),
        containerColor = DarkNavyBg,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(16.dp)
            )
        },
        topBar = {
            HandMorphTopBar(
                displayMode = uiState.displayMode,
                currentScreen = uiState.currentScreen,
                onModeToggle = { viewModel.toggleDisplayMode() },
                onBackClick = { viewModel.navigateBack() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkNavyBg)
        ) {
            when (uiState.displayMode) {
                DisplayMode.CONTACT_SHEET -> {
                    // Panoramic 16:9 5-Screen Contact Sheet side-by-side
                    ContactSheetScreen(
                        currentSelectedStyle = uiState.selectedStyle,
                        activeFocusedScreen = uiState.currentScreen,
                        onScreenSelected = { screen ->
                            viewModel.navigateToScreen(screen)
                            viewModel.setDisplayMode(DisplayMode.INTERACTIVE_FLOW)
                        }
                    )
                }

                DisplayMode.INTERACTIVE_FLOW -> {
                    // Single screen interactive mobile flow with step transitions
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                    ) {
                        // Interactive Step Navigation Bar
                        InteractiveStepsHeader(
                            currentScreen = uiState.currentScreen,
                            onStepClick = { screen -> viewModel.navigateToScreen(screen) }
                        )

                        // Animated Screen Body
                        AnimatedContent(
                            targetState = uiState.currentScreen,
                            transitionSpec = {
                                if (targetState.stepNumber > initialState.stepNumber) {
                                    (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                                        slideOutHorizontally { width -> -width } + fadeOut()
                                    )
                                } else {
                                    (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                                        slideOutHorizontally { width -> width } + fadeOut()
                                    )
                                }
                            },
                            label = "screen_flow_anim"
                        ) { targetScreen ->
                            when (targetScreen) {
                                AppScreen.HOME -> {
                                    HomeScreen(
                                        onImageSelected = { uri -> viewModel.setCapturedImage(uri) },
                                        onProjectClick = { viewModel.navigateToScreen(AppScreen.RESULT_PREVIEW) }
                                    )
                                }

                                AppScreen.ANALYSIS -> {
                                    AnalysisScreen(
                                        progress = uiState.analysisProgress,
                                        onContinueClick = { viewModel.navigateToScreen(AppScreen.STYLE_SELECTION) }
                                    )
                                }

                                AppScreen.STYLE_SELECTION -> {
                                    StyleSelectionScreen(
                                        selectedStyle = uiState.selectedStyle,
                                        onSelectStyle = { style -> viewModel.selectStyle(style) },
                                        onProceedClick = { viewModel.navigateToScreen(AppScreen.RESULT_PREVIEW) }
                                    )
                                }

                                AppScreen.RESULT_PREVIEW -> {
                                    ResultPreviewScreen(
                                        currentStyle = uiState.selectedStyle,
                                        onRegenerateClick = {
                                            viewModel.triggerFeedback("Regenerated variations for ${uiState.selectedStyle.name}")
                                        },
                                        onTryAnotherStyleClick = {
                                            viewModel.navigateToScreen(AppScreen.STYLE_SELECTION)
                                        },
                                        onContinueToDownloadClick = {
                                            viewModel.navigateToScreen(AppScreen.DOWNLOAD)
                                        }
                                    )
                                }

                                AppScreen.DOWNLOAD -> {
                                    DownloadScreen(
                                        currentStyle = uiState.selectedStyle,
                                        onDownloadPdfClick = {
                                            viewModel.triggerFeedback("PDF exported to /Downloads/HandMorph_Bio201.pdf (300 DPI)")
                                        },
                                        onDownloadPngClick = {
                                            viewModel.triggerFeedback("PNG image saved to Photos/HandMorph")
                                        },
                                        onSaveToMyStylesClick = {
                                            viewModel.triggerFeedback("Saved '${uiState.selectedStyle.name}' to My Styles preset library")
                                        },
                                        onShareClick = {
                                            viewModel.triggerFeedback("Opening system share sheet...")
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HandMorphTopBar(
    displayMode: DisplayMode,
    currentScreen: AppScreen,
    onModeToggle: () -> Unit,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(Color(0xFF070B14))
            .border(1.dp, Color(0xFF131F36))
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (displayMode == DisplayMode.INTERACTIVE_FLOW && currentScreen != AppScreen.HOME) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(DarkNavyCard)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = CyanBright,
                        modifier = Modifier.size(18.dp)
                    )
                }
            } else {
                Image(
                    painter = painterResource(id = R.drawable.handmorph_icon),
                    contentDescription = "HandMorph AI Logo",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, CyanNeon.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                )
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "HANDMORPH",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextWhite,
                        letterSpacing = 0.8.sp
                    )
                    Text(
                        text = "AI",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = CyanNeon
                    )
                }
                Text(
                    text = if (displayMode == DisplayMode.CONTACT_SHEET) {
                        "5-Screen Showcase"
                    } else {
                        "Screen ${currentScreen.stepNumber} of 5: ${currentScreen.title}"
                    },
                    fontSize = 9.sp,
                    color = CyanBright
                )
            }
        }

        // Segmented View Switcher Pill
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(DarkNavyCard)
                .border(1.dp, DarkNavyCardBorder, RoundedCornerShape(20.dp))
                .clickable { onModeToggle() }
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Option 1: 5-Screen Contact Sheet
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (displayMode == DisplayMode.CONTACT_SHEET) CyanNeon else Color.Transparent
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.GridView,
                    contentDescription = "5-Screen Contact Sheet",
                    tint = if (displayMode == DisplayMode.CONTACT_SHEET) Color(0xFF04101E) else TextMuted,
                    modifier = Modifier.size(13.dp)
                )
                Text(
                    text = "5 Screens",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (displayMode == DisplayMode.CONTACT_SHEET) Color(0xFF04101E) else TextLight
                )
            }

            // Option 2: Interactive Flow
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (displayMode == DisplayMode.INTERACTIVE_FLOW) CyanNeon else Color.Transparent
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Smartphone,
                    contentDescription = "Interactive Flow",
                    tint = if (displayMode == DisplayMode.INTERACTIVE_FLOW) Color(0xFF04101E) else TextMuted,
                    modifier = Modifier.size(13.dp)
                )
                Text(
                    text = "Interactive",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (displayMode == DisplayMode.INTERACTIVE_FLOW) Color(0xFF04101E) else TextLight
                )
            }
        }
    }
}

@Composable
fun InteractiveStepsHeader(
    currentScreen: AppScreen,
    onStepClick: (AppScreen) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0B1220))
            .border(0.75.dp, Color(0xFF162540))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppScreen.values().forEach { screen ->
            val isCurrent = screen == currentScreen
            val isCompleted = screen.stepNumber < currentScreen.stepNumber

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (isCurrent) Color(0xFF132A52) else Color.Transparent
                    )
                    .clickable { onStepClick(screen) }
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                isCurrent -> CyanNeon
                                isCompleted -> ElectricBlue
                                else -> Color(0xFF1E2D48)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${screen.stepNumber}",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            isCurrent -> Color(0xFF04101E)
                            isCompleted -> TextWhite
                            else -> TextDim
                        }
                    )
                }
                Text(
                    text = when (screen) {
                        AppScreen.HOME -> "Home"
                        AppScreen.ANALYSIS -> "Analysis"
                        AppScreen.STYLE_SELECTION -> "Styles"
                        AppScreen.RESULT_PREVIEW -> "Preview"
                        AppScreen.DOWNLOAD -> "Download"
                    },
                    fontSize = 9.5.sp,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                    color = if (isCurrent) CyanBright else TextDim
                )
            }
        }
    }
}
