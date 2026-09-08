package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.model.CyberRepository
import com.example.ui.BrayanTechApp
import com.example.ui.theme.MyApplicationTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun testAppNameString() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("brayanTech", appName)
  }

  @Test
  fun testCyberRepositoryDataIntegrity() {
    assertTrue(CyberRepository.courses.isNotEmpty())
    assertTrue(CyberRepository.ctfLabs.isNotEmpty())
    assertTrue(CyberRepository.glossaryTerms.isNotEmpty())
    assertTrue(CyberRepository.certificationQuizQuestions.isNotEmpty())

    val terminalLab = CyberRepository.ctfLabs.find { it.labType == com.example.model.LabType.TERMINAL_CLI }
    assertNotNull(terminalLab)
    assertEquals("10.10.14.88 (Target)", terminalLab?.targetHost)
  }

  @Test
  fun testBrayanTechAppNavigation() {
    composeTestRule.setContent {
      MyApplicationTheme {
        BrayanTechApp()
      }
    }

    // Check header logo and title
    composeTestRule.onNodeWithText("brayanTech").assertIsDisplayed()

    // Navigate to Formations
    composeTestRule.onNodeWithTag("tab_courses").performClick()
    composeTestRule.onNodeWithTag("courses_catalog_screen").assertIsDisplayed()

    // Navigate to Labs CTF
    composeTestRule.onNodeWithTag("tab_labs").performClick()
    composeTestRule.onNodeWithTag("labs_list_screen").assertIsDisplayed()

    // Navigate to Certifications
    composeTestRule.onNodeWithTag("tab_certifications").performClick()
    composeTestRule.onNodeWithTag("certifications_screen").assertIsDisplayed()

    // Navigate to Outils
    composeTestRule.onNodeWithTag("tab_toolbox").performClick()
    composeTestRule.onNodeWithTag("toolbox_screen").assertIsDisplayed()
  }
}
