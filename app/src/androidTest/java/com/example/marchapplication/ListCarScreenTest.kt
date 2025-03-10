package com.example.marchapplication

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marchapplication.Navigation.AppNavigator
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
        composeTestRule.setContent {
            val navController = rememberNavController()
            ListCarScreen(navController = navController)
        }
        composeTestRule.onNodeWithTag("ListCarScreen").assertExists()
        composeTestRule.onNodeWithTag("CarListText").assertExists()
        composeTestRule.onNodeWithTag("BackButton").assertExists()
        composeTestRule.onNodeWithTag("CarGrid").assertExists()
    }
    @Test
    fun testBackButtonNavigatesBack() {
        composeTestRule.setContent {
            AppNavigator()
            val navController = rememberNavController()
            ListCarScreen(navController = navController)
        }
        composeTestRule.onNodeWithTag("BackButton", useUnmergedTree = true)
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("CatchMazdaText").assertExists()
    }
}