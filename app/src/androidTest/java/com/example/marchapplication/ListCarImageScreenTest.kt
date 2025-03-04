package com.example.marchapplication

import android.app.Application
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marchapplication.ui.screens.ListCarImageScreen
import com.example.marchapplication.ViewModel.CarViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListCarImageScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testListCarImageScreenComponents() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = CarViewModel(application)
        composeTestRule.setContent {
            val navController = rememberNavController()
            ListCarImageScreen(navController = navController, folderName = "TestFolder", viewModel = viewModel)
        }

        // Check if Column with test tag "ListCarImageScreen" is displayed
        composeTestRule.onNodeWithTag("ListCarImageScreen").assertExists()

        // Check if TextCustom with test tag "FolderNameText" is displayed
        composeTestRule.onNodeWithTag("FolderNameText").assertExists()

        // Check if ButtonCustom with test tag "BackButton" is displayed
        composeTestRule.onNodeWithTag("BackButton").assertExists()

        // Check if Box with test tag "NoImagesBox" is displayed when there are no images
        composeTestRule.onNodeWithTag("NoImagesBox").assertExists()

        // Check if Text with test tag "NoImagesText" is displayed when there are no images
        composeTestRule.onNodeWithTag("NoImagesText").assertExists()

        // Check if LazyVerticalGrid with test tag "ImageGrid" is displayed when there are images
//        composeTestRule.onNodeWithTag("ImageGrid").assertExists()

        // Check if Column with test tag "ImageGridItem" is displayed for each image
//        composeTestRule.onNodeWithTag("ImageGridItem").assertExists()

        // Check if Box with test tag "ImageBox" is displayed for each image
//        composeTestRule.onNodeWithTag("ImageBox").assertExists()

        // Check if AsyncImage with test tag "AsyncImage" is displayed for each image
//        composeTestRule.onNodeWithTag("AsyncImage").assertExists()

        // Check if TextCustom with test tag "DisplayTimeText" is displayed for each image
//        composeTestRule.onNodeWithTag("DisplayTimeText").assertExists()
    }
}