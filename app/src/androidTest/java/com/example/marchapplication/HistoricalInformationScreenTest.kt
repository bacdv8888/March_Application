package com.example.marchapplication

import android.app.Application
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marchapplication.ui.screens.HistoricalInformationScreen
import com.example.marchapplication.ViewModel.HistoricalInformationViewModel
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
            val navController = rememberNavController()
            HistoricalInformationScreen(navController = navController, folderName = "TestFolder", viewModel = viewModel)
        }

        // Check if TextCustom with test tag "HistoryText" is displayed
        composeTestRule.onNodeWithTag("HistoryText").assertExists()

        // Check if Image with test tag "AvatarImage" is displayed
        composeTestRule.onNodeWithTag("AvatarImage").assertExists()

        // Check if TextCustom with test tag "FolderNameText" is displayed
        composeTestRule.onNodeWithTag("FolderNameText").assertExists()

        // Check if ButtonCustom with test tag "BackButton" is displayed
        composeTestRule.onNodeWithTag("BackButton").assertExists()

        // Check if ButtonIconPlay with test tag "PlayButton" is displayed
        composeTestRule.onNodeWithTag("PlayButton").assertExists()

        // Check if TextCustom with test tag "ContentText" is displayed
        composeTestRule.onNodeWithTag("ContentText").assertExists()
    }
}