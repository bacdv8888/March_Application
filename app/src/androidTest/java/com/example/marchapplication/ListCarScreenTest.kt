package com.example.marchapplication

import android.app.Application
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marchapplication.ViewModel.CarViewModel
import com.example.marchapplication.ui.screens.ListCarImageScreen
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
    fun testClickFolderNavigatesToListCarImageScreen() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val testNavController = TestNavHostController(application).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }
        composeTestRule.setContent {
            NavHost(
                navController = testNavController,
                startDestination = "listCarScreen"
            ) {
                composable("listCarScreen") {
                    ListCarScreen(navController = testNavController)
                }
                composable("listCarImageScreen/{folderName}") { backStackEntry ->
                    val folderName = backStackEntry.arguments?.getString("folderName") ?: ""
                    ListCarImageScreen(
                        navController = testNavController,
                        folderName = folderName,
                        viewModel = CarViewModel(application)
                    )
                }
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("ListCarScreen", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("CarGrid", useUnmergedTree = true).assertExists()
        composeTestRule.onAllNodesWithTag("CarImageBox").onFirst().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("ListCarImageScreen", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("BackButton", useUnmergedTree = true).assertExists().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("ListCarScreen", useUnmergedTree = true).assertExists()
    }
}