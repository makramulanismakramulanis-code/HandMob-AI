package com.example.model

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

enum class AppScreen(val stepNumber: Int, val title: String, val subtitle: String) {
    HOME(1, "Home Screen", "Launchpad & Recent Projects"),
    ANALYSIS(2, "Handwriting Analysis", "AI Neural Scan & Extraction"),
    STYLE_SELECTION(3, "Style Selection", "7 Personalized Handwriting Presets"),
    RESULT_PREVIEW(4, "Result Preview", "Side-by-Side Comparison"),
    DOWNLOAD(5, "Download Screen", "Export High-Res PDF/PNG")
}

enum class DisplayMode {
    CONTACT_SHEET, // 5 mobile screens side-by-side in landscape 16:9 view
    INTERACTIVE_FLOW // single mobile screen in full interactive use
}

enum class CompareMode {
    SIDE_BY_SIDE,
    INTERACTIVE_SLIDER,
    FLIP_TOGGLE
}

data class HandwritingStyle(
    val id: String,
    val name: String,
    val tag: String,
    val description: String,
    val badge: String? = null,
    val sampleSnippet: String = "The intricate dance of spacetime is described by both complex mathematics and elegant narrative entanglements.",
    val fontStyle: FontStyle = FontStyle.Normal,
    val fontWeight: FontWeight = FontWeight.Normal,
    val fontFamily: FontFamily = FontFamily.SansSerif,
    val letterSpacingSp: Float = 0.5f,
    val slantDegree: Float = 0f,
    val inkHex: Long = 0xFF1E3A8A // Dark fountain pen blue
)

val SampleParagraphText = "The intricate dance of spacetime is described by both complex mathematics and elegant narrative entanglements, creating a profound relationship between observable data and poetic understanding. Stars fusing lighter elements provide cosmic structure for living worlds."

val HandwritingStylesList = listOf(
    HandwritingStyle(
        id = "original",
        name = "Original Personal Style",
        tag = "Baseline Signature",
        description = "Exact captured stroke pressure and natural notebook flow.",
        badge = "Original",
        fontFamily = FontFamily.Cursive,
        fontStyle = FontStyle.Normal,
        slantDegree = 2f,
        inkHex = 0xFF1D4ED8
    ),
    HandwritingStyle(
        id = "italic",
        name = "Personal Italic",
        tag = "Dynamic Slant",
        description = "Precise 14° aerodynamic forward angle with sharp terminals.",
        badge = "Fast Notes",
        fontFamily = FontFamily.Serif,
        fontStyle = FontStyle.Italic,
        slantDegree = 14f,
        inkHex = 0xFF0F172A
    ),
    HandwritingStyle(
        id = "cursive",
        name = "Personal Cursive",
        tag = "Connected Script",
        description = "Seamless continuous ligatures and rhythmic calligraphy loops.",
        badge = "Most Popular",
        fontFamily = FontFamily.Cursive,
        fontStyle = FontStyle.Italic,
        slantDegree = 8f,
        inkHex = 0xFF1E3A8A
    ),
    HandwritingStyle(
        id = "neat",
        name = "Personal Neat",
        tag = "Architectural Print",
        description = "Ultra-consistent x-height and razor-sharp baseline discipline.",
        badge = "Exam Ready",
        fontFamily = FontFamily.SansSerif,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Medium,
        letterSpacingSp = 1.0f,
        slantDegree = 0f,
        inkHex = 0xFF0284C7
    ),
    HandwritingStyle(
        id = "rounded",
        name = "Personal Rounded",
        tag = "Friendly Aesthetic",
        description = "Soft bubble curves, open counters and relaxed friendly loops.",
        badge = "Study Guides",
        fontFamily = FontFamily.Default,
        fontStyle = FontStyle.Normal,
        letterSpacingSp = 0.6f,
        slantDegree = 1f,
        inkHex = 0xFF2563EB
    ),
    HandwritingStyle(
        id = "elegant",
        name = "Personal Elegant",
        tag = "Flourished Copperplate",
        description = "Refined fountain pen stroke contrast with graceful ascenders.",
        badge = "Formal",
        fontFamily = FontFamily.Serif,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.SemiBold,
        slantDegree = 12f,
        inkHex = 0xFF1E293B
    ),
    HandwritingStyle(
        id = "professional",
        name = "Personal Professional",
        tag = "Executive Manuscript",
        description = "High-legibility drafting script suited for formal engineering logs.",
        badge = "Executive",
        fontFamily = FontFamily.Monospace,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Normal,
        letterSpacingSp = 0.2f,
        slantDegree = 0f,
        inkHex = 0xFF0F172A
    )
)

data class RecentProject(
    val id: String,
    val title: String,
    val subject: String,
    val date: String,
    val pageCount: Int,
    val styleApplied: String,
    val wordCount: Int,
    val previewImageRes: Int = R.drawable.notebook_sample
)

val SampleRecentProjects = listOf(
    RecentProject(
        id = "p1",
        title = "Bio 201: Cellular Respiration",
        subject = "Biochemistry Notes",
        date = "Today, 10:24 AM",
        pageCount = 3,
        styleApplied = "Personal Neat",
        wordCount = 428,
        previewImageRes = R.drawable.notebook_sample
    ),
    RecentProject(
        id = "p2",
        title = "Physics III: Spacetime Theory",
        subject = "Astrophysics Lecture",
        date = "Yesterday",
        pageCount = 2,
        styleApplied = "Personal Elegant",
        wordCount = 612,
        previewImageRes = R.drawable.doc_illustration
    ),
    RecentProject(
        id = "p3",
        title = "Calculus: Stokes & Green Theorems",
        subject = "Math Seminar",
        date = "Nov 04, 2026",
        pageCount = 5,
        styleApplied = "Personal Cursive",
        wordCount = 890,
        previewImageRes = R.drawable.notebook_sample
    )
)
