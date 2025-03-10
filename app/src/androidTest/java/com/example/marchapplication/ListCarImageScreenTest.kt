package com.example.marchapplication

import android.app.Application
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.marchapplication.Data.Photo
import com.example.marchapplication.ui.screens.ListCarImageScreen
import com.example.marchapplication.ViewModel.CarViewModel
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ListCarImageScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testListCarImageScreenComponents() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = CarViewModel(application)
        (viewModel.imageList as? MutableStateFlow)?.value = emptyList()

        composeTestRule.setContent {
            val navController = rememberNavController()
            ListCarImageScreen(
                navController = navController,
                folderName = "TestFolder",
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag("ListCarImageScreen").assertExists()
        composeTestRule.onNodeWithTag("FolderNameText").assertExists()
        composeTestRule.onNodeWithTag("BackButton").assertExists()
    }

    @Test
    fun testListCarImageScreenNoImages() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = CarViewModel(application)
        (viewModel.imageList as? MutableStateFlow)?.value = emptyList()

        composeTestRule.setContent {
            val navController = rememberNavController()
            ListCarImageScreen(
                navController = navController,
                folderName = "TestFolder",
                viewModel = viewModel
            )
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("NoImagesText", useUnmergedTree = true)
            .assertExists()
            .assertTextContains("No images found for 'TestFolder'.")
    }

    @Test
    fun testListCarImageScreenWithImages() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = CarViewModel(application)
        (viewModel.imageList as? MutableStateFlow)?.value = emptyList()
        composeTestRule.setContent {
            val navController = rememberNavController()
            ListCarImageScreen(
                navController = navController,
                folderName = "TestFolder",
                viewModel = viewModel
            )
        }

        val fakePhoto = Photo(
            id = 1,
            NameProduct = "Test Product",
            FilePath = "dummy/path",
            Image = 1,
            CarName = "TestFolder",
            CapturedBy = "Tester",
            DateCaptured = "2021-10-10",
            Location = "TestLocation",
            TextEN = "TestText",
            TextJP = null
        )
        composeTestRule.runOnIdle {
            (viewModel.imageList as? MutableStateFlow)?.value = listOf(fakePhoto)
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("AsyncImage", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("DisplayTimeText", useUnmergedTree = true)
            .assertExists()
            .assertTextContains("2021-10-10")
    }
}
