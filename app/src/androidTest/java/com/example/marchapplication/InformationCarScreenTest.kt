package com.example.marchapplication

import android.app.Application
import android.content.Intent
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.intent.matcher.IntentMatchers.hasType
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marchapplication.ui.screens.InformationCarScreen
import com.example.marchapplication.ViewModel.CarViewModel
import com.example.marchapplication.ViewModel.HistoricalInformationViewModel
import com.example.marchapplication.ui.screens.HistoricalInformationScreen
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class InformationCarScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testInformationCarScreenComponents() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = CarViewModel(application)
        val imagePath = "test/path/to/saved_image.jpg"

        viewModel.loadPhotoData(imagePath)

        composeTestRule.setContent {
            val navController = rememberNavController()
            InformationCarScreen(navController = navController, imagePath = imagePath, viewModel = viewModel)
        }

        composeTestRule.onNodeWithTag("DetailsText").assertExists()
        composeTestRule.onNodeWithTag("CarImage").assertExists()
        composeTestRule.onNodeWithText("車種名:").assertExists()
        composeTestRule.onNodeWithText("撮影日:").assertExists()
        composeTestRule.onNodeWithText("撮影場所:").assertExists()
        composeTestRule.onNodeWithText("オーナー:").assertExists()
        composeTestRule.onNodeWithTag("SaveButton").assertExists()
        composeTestRule.onNodeWithTag("BackButton").assertExists()
        composeTestRule.onNodeWithTag("HistoryButton").assertExists()
        composeTestRule.onNodeWithTag("ShareButton").assertExists()
    }

    @Test
    fun testSaveButtonFunctionality() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = CarViewModel(application)
        val imagePath = "test/path/to/saved_image.jpg"

        viewModel.loadPhotoData(imagePath)

        composeTestRule.setContent {
            val navController = rememberNavController()
            InformationCarScreen(navController = navController, imagePath = imagePath, viewModel = viewModel)
        }
        viewModel.updateCarName("Test Car")
        viewModel.updateLocation("Test Location")
        viewModel.updateCapturedBy("Tester")
        composeTestRule.onNodeWithTag("SaveButton").performClick()

        assert(viewModel.carName.value == "Test Car")
        assert(viewModel.location.value == "Test Location")
        assert(viewModel.capturedBy.value == "Tester")
    }

    @Test
    fun testShareButtonFunctionality() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = CarViewModel(application)

        val imagesDir = File(application.filesDir, "Images")
        imagesDir.mkdirs()

        val testFile = File(imagesDir, "saved_image.jpg").apply {
            writeText("Fake Data")
        }
        val imagePath = testFile.absolutePath

        val testCarName = "Test Car"
        val testDateCaptured = "2023-01-01"
        val testLocation = "Tokyo"
        val testCapturedBy = "Tester"

        viewModel.updateCarName(testCarName)
        viewModel.updateDateCaptured(testDateCaptured)
        viewModel.updateLocation(testLocation)
        viewModel.updateCapturedBy(testCapturedBy)
        composeTestRule.setContent {
            val navController = rememberNavController()
            InformationCarScreen(
                navController = navController,
                imagePath = imagePath,
                viewModel = viewModel
            )
        }
        Intents.init()
        composeTestRule.onNodeWithTag("ShareButton").performClick()
        intended(
            allOf(
                hasAction(Intent.ACTION_CHOOSER),
                hasExtra(
                    `is`(Intent.EXTRA_INTENT),
                    allOf(
                        hasAction(Intent.ACTION_SEND),
                        hasType("image/*"),
                        hasExtraWithKey(Intent.EXTRA_STREAM),
                        hasExtra(
                            equalTo(Intent.EXTRA_TEXT),
                            `is`(
                                """
                            車種名: $testCarName
                            撮影日: $testDateCaptured
                            撮影場所: $testLocation
                            オーナー: $testCapturedBy
                            """.trimIndent()
                            )
                        )
                    )
                )
            )
        )
        Intents.release()
    }

    @Test
    fun testHistoryButtonNavigation() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = CarViewModel(application)
        val historicalInformationViewModel = HistoricalInformationViewModel(application)
        val testImagePath = "dummy/TestFolder/image.jpg"
        val testNavController = TestNavHostController(application).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }
        composeTestRule.setContent {
            NavHost(
                navController = testNavController,
                startDestination = "informationCarScreen/{imagePath}"
            ) {
                composable(
                    route = "informationCarScreen/{imagePath}",
                    arguments = listOf(navArgument("imagePath") { defaultValue = testImagePath })
                ) {
                    InformationCarScreen(
                        navController = testNavController,
                        imagePath = testImagePath,
                        viewModel = viewModel
                    )
                }
                composable(
                    route = "historicalInformationScreen/{folderName}",
                    arguments = listOf(navArgument("folderName") { type = NavType.StringType })
                ) { backStackEntry ->
                    val folderName = backStackEntry.arguments?.getString("folderName") ?: ""
                    HistoricalInformationScreen(
                        navController = testNavController,
                        folderName = folderName,
                        viewModel = historicalInformationViewModel
                    )
                }
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("DetailsText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("HistoryButton", useUnmergedTree = true).assertExists().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("HistoryText", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("BackButton", useUnmergedTree = true).assertExists().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("DetailsText", useUnmergedTree = true).assertExists()
    }
}