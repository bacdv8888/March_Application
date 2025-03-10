package com.example.marchapplication

import android.app.Application
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marchapplication.ui.screens.HistoricalInformationScreen
import com.example.marchapplication.ViewModel.HistoricalInformationViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HistoricalInformationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHistoricalInformationScreenComponents() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = HistoricalInformationViewModel(application)
        composeTestRule.setContent {
            val navController = TestNavHostController(application).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            HistoricalInformationScreen(
                navController = navController,
                folderName = "TestFolder",
                viewModel = viewModel
            )
        }
        // Check UI components
        composeTestRule.onNodeWithTag("HistoryText").assertExists()
        composeTestRule.onNodeWithTag("AvatarImage").assertExists()
        composeTestRule.onNodeWithTag("FolderNameText").assertExists()
        composeTestRule.onNodeWithTag("BackButton").assertExists()
        composeTestRule.onNodeWithTag("PlayButton").assertExists()
        composeTestRule.onNodeWithTag("ContentText").assertExists()
    }

    @Test
    fun testLoadCarDataShowsFolderName() = runTest {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val testFolder = "MyTestFolder"
        val viewModel = HistoricalInformationViewModel(application)

        composeTestRule.setContent {
            val navController = TestNavHostController(application).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            HistoricalInformationScreen(
                navController = navController,
                folderName = testFolder,
                viewModel = viewModel
            )
        }
        composeTestRule.onNodeWithTag("FolderNameText")
            .assertExists()
            .assertTextEquals(testFolder)
    }

    @Test
    fun testPlayButtonTogglesReadingState() = runTest {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = HistoricalInformationViewModel(application)

        assertFalse(viewModel.isPlaying.value)

        composeTestRule.setContent {
            val navController = TestNavHostController(application).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            HistoricalInformationScreen(
                navController = navController,
                folderName = "TestFolder",
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag("PlayButton").performClick()

        composeTestRule.waitForIdle()
        assertTrue(viewModel.isPlaying.value)

        composeTestRule.onNodeWithTag("PlayButton").performClick()
        composeTestRule.waitForIdle()
        assertFalse(viewModel.isPlaying.value)
    }
}
