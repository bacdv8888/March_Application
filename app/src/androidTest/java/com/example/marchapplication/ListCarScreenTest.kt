package com.example.marchapplication

import android.app.Application
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marchapplication.ui.screens.ListCarScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListCarScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testListCarScreenComponents() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        composeTestRule.setContent {
            val navController = rememberNavController()
            ListCarScreen(navController = navController)
        }

        // Check if Column with test tag "ListCarScreen" is displayed
        composeTestRule.onNodeWithTag("ListCarScreen").assertExists()

        // Check if TextCustom with test tag "CarListText" is displayed
        composeTestRule.onNodeWithTag("CarListText").assertExists()

        // Check if ButtonCustom with test tag "BackButton" is displayed
        composeTestRule.onNodeWithTag("BackButton").assertExists()

        // Check if LazyVerticalGrid with test tag "CarGrid" is displayed
        composeTestRule.onNodeWithTag("CarGrid").assertExists()

        // Check if Column with test tag "CarGridItem" is displayed for each car
//        composeTestRule.onNodeWithTag("CarGridItem").assertExists()

        // Check if Box with test tag "CarImageBox" is displayed for each car
//        composeTestRule.onNodeWithTag("CarImageBox").assertExists()

        // Check if Image with test tag "CarImage" is displayed for each car
//        composeTestRule.onNodeWithTag("CarImage").assertExists()

        // Check if TextCustom with test tag "CarImageText" is displayed for each car
//        composeTestRule.onNodeWithTag("CarImageText").assertExists()
    }
}