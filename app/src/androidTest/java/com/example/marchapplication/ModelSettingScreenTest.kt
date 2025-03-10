package com.example.marchapplication

import android.Manifest
import android.net.Uri
import android.app.Application
import android.provider.MediaStore
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.rule.GrantPermissionRule
import com.example.marchapplication.ui.screens.HomeScreen
import com.example.marchapplication.ui.screens.ModelSettingScreen

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ModelSettingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @Test
    fun testModelSettingScreenComponents() {
        val dummyUri = Uri.parse("dummy://image")
        composeTestRule.setContent {
            val testNavController = TestNavHostController(ApplicationProvider.getApplicationContext())
            testNavController.navigatorProvider.addNavigator(ComposeNavigator())
            ModelSettingScreen(navController = testNavController, imageUri = dummyUri)
        }
        composeTestRule.onNodeWithTag("ModelSettingScreen", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("CarSelectionText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("CarDropdown", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("CancelButton", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("OkButton", useUnmergedTree = true).assertExists()
    }

    @Test
    fun testDropdownSelection() {
        val dummyUri = Uri.parse("dummy://image")
        composeTestRule.setContent {
            ModelSettingScreen(
                navController = TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                },
                imageUri = dummyUri
            )
        }
        composeTestRule.onNodeWithTag("CarDropdown", useUnmergedTree = true).performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("CarDropdownMenu", useUnmergedTree = true).assertExists()
        composeTestRule.onAllNodesWithTag("CarDropdownMenuItem", useUnmergedTree = true)[0].performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun testOkButtonNavigatesHome() {
        val dummyUri = Uri.parse("dummy://image")
        val context = ApplicationProvider.getApplicationContext<Application>()
        val testNavController = TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeTestRule.setContent {
            NavHost(
                navController = testNavController,
                startDestination = "modelSettingScreen"
            ) {
                composable("modelSettingScreen") {
                    ModelSettingScreen(navController = testNavController, imageUri = dummyUri)
                }
                composable("homeScreen") {
                    HomeScreen(navController = testNavController)
                }
            }
        }

        composeTestRule.onNodeWithTag("OkButton", useUnmergedTree = true).performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("HomeScreen", useUnmergedTree = true).assertExists()
    }

    @Test
    fun testCancelButtonDoesNotCrash() {
        Intents.init()
        try {
            val dummyUri = Uri.parse("dummy://image")
            composeTestRule.setContent {
                ModelSettingScreen(
                    navController = TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
                        navigatorProvider.addNavigator(ComposeNavigator())
                    },
                    imageUri = dummyUri
                )
            }
            composeTestRule.onNodeWithTag("CancelButton", useUnmergedTree = true).performClick()
            composeTestRule.waitForIdle()
            intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE))
        } finally {
            Intents.release()
        }
    }
}
