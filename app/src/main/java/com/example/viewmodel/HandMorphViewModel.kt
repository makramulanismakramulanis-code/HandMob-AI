package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AppScreen
import com.example.model.DisplayMode
import com.example.model.HandwritingStyle
import com.example.model.HandwritingStylesList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HandMorphUiState(
    val currentScreen: AppScreen = AppScreen.HOME,
    val displayMode: DisplayMode = DisplayMode.INTERACTIVE_FLOW, // Start in interactive flow mode
    val selectedStyle: HandwritingStyle = HandwritingStylesList[2], // Personal Cursive
    val analysisProgress: Float = 0.88f,
    val isAnalyzing: Boolean = false,
    val feedbackMessage: String? = null,
    val capturedImageUri: android.net.Uri? = null
)

class HandMorphViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HandMorphUiState())
    val uiState: StateFlow<HandMorphUiState> = _uiState.asStateFlow()

    fun setDisplayMode(mode: DisplayMode) {
        _uiState.update { it.copy(displayMode = mode) }
    }

    fun toggleDisplayMode() {
        _uiState.update {
            it.copy(
                displayMode = if (it.displayMode == DisplayMode.CONTACT_SHEET) {
                    DisplayMode.INTERACTIVE_FLOW
                } else {
                    DisplayMode.CONTACT_SHEET
                }
            )
        }
    }

    fun navigateToScreen(screen: AppScreen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    fun selectStyle(style: HandwritingStyle) {
        _uiState.update { it.copy(selectedStyle = style) }
    }

    fun startAnalysis() {
        _uiState.update {
            it.copy(
                currentScreen = AppScreen.ANALYSIS,
                analysisProgress = 0.1f,
                isAnalyzing = true
            )
        }
        viewModelScope.launch {
            for (step in 1..9) {
                delay(300)
                _uiState.update {
                    it.copy(analysisProgress = (step / 10f) + 0.08f)
                }
            }
            _uiState.update { it.copy(analysisProgress = 1.0f, isAnalyzing = false) }
        }
    }

    fun triggerFeedback(message: String) {
        _uiState.update { it.copy(feedbackMessage = message) }
    }

    fun dismissFeedback() {
        _uiState.update { it.copy(feedbackMessage = null) }
    }

    fun setCapturedImage(uri: android.net.Uri?) {
        _uiState.update { it.copy(capturedImageUri = uri) }
        if (uri != null) {
            startAnalysis()
        }
    }

    fun navigateBack(): Boolean {
        val current = _uiState.value.currentScreen
        if (_uiState.value.displayMode == DisplayMode.INTERACTIVE_FLOW) {
            when (current) {
                AppScreen.DOWNLOAD -> {
                    _uiState.update { it.copy(currentScreen = AppScreen.RESULT_PREVIEW) }
                    return true
                }
                AppScreen.RESULT_PREVIEW -> {
                    _uiState.update { it.copy(currentScreen = AppScreen.STYLE_SELECTION) }
                    return true
                }
                AppScreen.STYLE_SELECTION -> {
                    _uiState.update { it.copy(currentScreen = AppScreen.ANALYSIS) }
                    return true
                }
                AppScreen.ANALYSIS -> {
                    _uiState.update { it.copy(currentScreen = AppScreen.HOME) }
                    return true
                }
                AppScreen.HOME -> {
                    // Switch to contact sheet overview
                    _uiState.update { it.copy(displayMode = DisplayMode.CONTACT_SHEET) }
                    return true
                }
            }
        }
        return false
    }
}
