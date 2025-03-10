package com.example.marchapplication

import android.Manifest
import android.content.Context
import android.provider.MediaStore
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.example.marchapplication.Navigation.AppNavigator
import com.example.marchapplication.ui.screens.HomeScreen
import com.example.marchapplication.utils.LocaleHelper
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @Test
    fun testHomeScreenComponents() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            HomeScreen(navController = navController)
        }

        composeTestRule.onNodeWithTag("CatchMazdaText").assertExists()
        composeTestRule.onNodeWithTag("CatchButton").assertExists()
        composeTestRule.onNodeWithTag("ListButton").assertExists()
        composeTestRule.onNodeWithTag("LanguageSwitchButton").assertExists()
    }

    @Test
    fun testCameraButtonLaunchesCamera() {
        Intents.init()
        try {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val navController = TestNavHostController(context).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            composeTestRule.setContent {
                HomeScreen(navController = navController)
            }
            // Wait for the UI to become idle
            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithTag("CatchButton", useUnmergedTree = true).performClick()

            intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE))
        } finally {
            Intents.release()
        }
    }

    @Test
    fun testLanguageSwitchButtonClick() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val navController = TestNavHostController(context)
        composeTestRule.setContent {
            HomeScreen(navController = navController)
        }

        val currentLanguage = LocaleHelper.getSavedLanguage(context)
        composeTestRule.onNodeWithTag("LanguageSwitchButton")
            .assertExists()
            .performClick()
        val newLanguage = LocaleHelper.getSavedLanguage(context)
        assertNotEquals(currentLanguage, newLanguage)
    }

    @Test
    fun testListButtonNavigatesToListCarScreen() {
        composeTestRule.setContent {
            AppNavigator()
        }

        composeTestRule.onNodeWithTag("ListButton", useUnmergedTree = true)
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("ListCarScreen").assertExists()
    }
}