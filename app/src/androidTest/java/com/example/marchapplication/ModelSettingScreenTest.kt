package com.example.marchapplication

import android.app.Application
import android.net.Uri
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marchapplication.ui.screens.ModelSettingScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ModelSettingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testModelSettingScreenComponents() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val imageUri = Uri.parse("test/path/to/image.jpg")
        composeTestRule.setContent {
            val navController = rememberNavController()
            ModelSettingScreen(navController = navController, imageUri = imageUri)
        }

        // Check if Column with test tag "ModelSettingScreen" is displayed
        composeTestRule.onNodeWithTag("ModelSettingScreen").assertExists()

        // Check if TextCustom with test tag "CarSelectionText" is displayed
        composeTestRule.onNodeWithTag("CarSelectionText").assertExists()

        // Check if TextField with test tag "CarDropdown" is displayed
        composeTestRule.onNodeWithTag("CarDropdown").assertExists()

        // Check if DropdownMenu with test tag "CarDropdownMenu" is displayed
//        composeTestRule.onNodeWithTag("CarDropdownMenu").assertExists()

        // Check if DropdownMenuItem with test tag "CarDropdownMenuItem" is displayed
//        composeTestRule.onNodeWithTag("CarDropdownMenuItem").assertExists()

        // Check if ButtonCustom with test tag "CancelButton" is displayed
        composeTestRule.onNodeWithTag("CancelButton").assertExists()

        // Check if ButtonCustom with test tag "OkButton" is displayed
        composeTestRule.onNodeWithTag("OkButton").assertExists()
    }
}