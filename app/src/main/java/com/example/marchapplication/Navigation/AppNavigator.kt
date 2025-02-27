package com.example.marchapplication.Navigation

import android.net.Uri
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marchapplication.ViewModel.CarViewModel
import com.example.marchapplication.ViewModel.HistoricalInformationViewModel
import com.example.marchapplication.ui.screens.HistoricalInformationScreen
import com.example.marchapplication.ui.screens.HomeScreen
import com.example.marchapplication.ui.screens.InformationCarScreen
import com.example.marchapplication.ui.screens.ListCarImageScreen
import com.example.marchapplication.ui.screens.ListCarScreen
import com.example.marchapplication.ui.screens.ModelSettingScreen

@Composable
fun AppNavigator(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val historicalInformationViewModel: HistoricalInformationViewModel = viewModel()
    val carViewModel: CarViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "homeScreen",
        modifier = modifier
    ) {

        composable("homeScreen",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(500)) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(500)) + fadeOut()
            }
        ) {
            HomeScreen(navController)
        }
        composable(
            "historicalInformationScreen/{folderName}",
            arguments = listOf(
                navArgument("folderName") { type = NavType.StringType }
            ),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))
            }
        ) {
                backStackEntry ->
            val folderName = backStackEntry.arguments?.getString("folderName") ?: ""
            HistoricalInformationScreen(navController, folderName, historicalInformationViewModel)
        }

        composable("informationCarScreen/{imagePath}",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))
            }
        ) {backStackEntry ->
            val imagePath = backStackEntry.arguments?.getString("imagePath")
            InformationCarScreen(navController, imagePath,carViewModel)
        }

        composable(
            route = "listCarImageScreen/{folderName}",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))
            }
        ) { backStackEntry ->
            // Lấy "folderName" từ arguments
            val folderName = backStackEntry.arguments?.getString("folderName") ?: ""
            ListCarImageScreen(navController, folderName, carViewModel)
        }

        composable("listCarScreen",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))
            }
        ) {
            ListCarScreen(navController)
        }
        composable(
            "modelSettingScreen/{uri}",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(500))
            }
        ) { backStackEntry ->
            val imageUri = backStackEntry.arguments?.getString("uri")?.let { Uri.parse(it) }
            ModelSettingScreen(navController, imageUri)
        }
    }
}

