package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.AppScreen
import com.example.model.HandwritingStylesList
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read app name from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("HandMorph AI", appName)
  }

  @Test
  fun `verify exactly 5 app screens configured`() {
    assertEquals(5, AppScreen.values().size)
    assertEquals(AppScreen.HOME, AppScreen.values()[0])
    assertEquals(AppScreen.ANALYSIS, AppScreen.values()[1])
    assertEquals(AppScreen.STYLE_SELECTION, AppScreen.values()[2])
    assertEquals(AppScreen.RESULT_PREVIEW, AppScreen.values()[3])
    assertEquals(AppScreen.DOWNLOAD, AppScreen.values()[4])
  }

  @Test
  fun `verify 7 distinct handwriting styles configured`() {
    assertEquals(7, HandwritingStylesList.size)
    val styleIds = HandwritingStylesList.map { it.id }
    assertTrue(styleIds.contains("original"))
    assertTrue(styleIds.contains("italic"))
    assertTrue(styleIds.contains("cursive"))
    assertTrue(styleIds.contains("neat"))
    assertTrue(styleIds.contains("rounded"))
    assertTrue(styleIds.contains("elegant"))
    assertTrue(styleIds.contains("professional"))
  }
}
