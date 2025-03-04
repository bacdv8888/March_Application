package com.example.marchapplication

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marchapplication.ui.screens.HomeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHomeScreenComponents() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            HomeScreen(navController = navController)
        }

        // Kiểm tra xem TextCustom với test tag "CatchMazdaText" có hiển thị không
        composeTestRule.onNodeWithTag("CatchMazdaText").assertExists()

        // Kiểm tra xem nút "Catch" với test tag "CatchButton" có hiển thị không
        composeTestRule.onNodeWithTag("CatchButton").assertExists()

        // Kiểm tra xem nút "List" với test tag "ListButton" có hiển thị không
        composeTestRule.onNodeWithTag("ListButton").assertExists()

        // Kiểm tra xem nút chuyển đổi ngôn ngữ với test tag "LanguageSwitchButton" có hiển thị không
        composeTestRule.onNodeWithTag("LanguageSwitchButton").assertExists()
    }
}