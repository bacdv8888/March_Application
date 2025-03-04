package com.example.marchapplication

import android.app.Application
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marchapplication.ui.screens.InformationCarScreen
import com.example.marchapplication.ViewModel.CarViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InformationCarScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testInformationCarScreenComponents() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = CarViewModel(application)
        composeTestRule.setContent {
            val navController = rememberNavController()
            InformationCarScreen(navController = navController, imagePath = "test/path/to/image.jpg", viewModel = viewModel)
        }

        // Check if TextCustom with test tag "DetailsText" is displayed
        composeTestRule.onNodeWithTag("DetailsText").assertExists()

        // Check if AsyncImage with test tag "CarImage" is displayed
        composeTestRule.onNodeWithTag("CarImage").assertExists()

        // Check if CustomTextField with test tag "CarNameField" is displayed
        composeTestRule.onNodeWithText("車種名:").assertExists()

        // Check if CustomTextField with test tag "DateCapturedField" is displayed
        composeTestRule.onNodeWithText("撮影日:").assertExists()

        // Check if CustomTextField with test tag "LocationField" is displayed
        composeTestRule.onNodeWithText("撮影場所:").assertExists()

        // Check if CustomTextField with test tag "CapturedByField" is displayed
        composeTestRule.onNodeWithText("オーナー:").assertExists()

        // Check if ButtonCustom with test tag "SaveButton" is displayed
        composeTestRule.onNodeWithTag("SaveButton").assertExists()

        // Check if ButtonCustom with test tag "BackButton" is displayed
        composeTestRule.onNodeWithTag("BackButton").assertExists()

        // Check if ButtonCustom with test tag "HistoryButton" is displayed
        composeTestRule.onNodeWithTag("HistoryButton").assertExists()

        // Check if ButtonCustom with test tag "ShareButton" is displayed
        composeTestRule.onNodeWithTag("ShareButton").assertExists()
    }
}