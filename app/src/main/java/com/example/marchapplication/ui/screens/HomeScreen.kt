package com.example.marchapplication.ui.screens

import android.Manifest
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.marchapplication.R

import com.example.marchapplication.utils.launchCamera
import com.example.marchapplication.utils.rememberCameraState
import com.example.marchapplication.ui.components.ButtonCustom

import com.example.marchapplication.ui.components.TextCustom
import com.yourapp.utils.LocaleHelper


@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current
    val cameraState = rememberCameraState(navController)

    val configuration = LocalConfiguration.current
    val paddingHeight = configuration.screenHeightDp.dp * 0.02f
    val paddingWidth = configuration.screenWidthDp.dp * 0.05f

    Column (
        modifier = Modifier
            //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
            .fillMaxSize()
            .background(Color.White)
            .padding(top = paddingHeight),
    ) {
        TextCustom(
            text = stringResource(id = R.string.catch_mazda),
            modifier = Modifier.padding(start = paddingWidth),
            fontSizeFactor = 0.04f
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .padding(bottom = paddingHeight),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            ButtonCustom(
                text = "Catch",
                onClick = {
                    if (cameraState.hasCameraPermission.value) {
                        launchCamera(context, cameraState.cameraLauncher, cameraState.capturedImageUri)
                    } else {
                        cameraState.cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
            )
            ButtonCustom(
                text = "List",
                onClick = { navController.navigate("listCarScreen") },
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Column( modifier = Modifier
            //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
            .padding(bottom = paddingHeight)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        )
        {
            ButtonCustom(
                text = stringResource(id = R.string.language_switch),
                onClick = {
                    val currentLanguage = LocaleHelper.getSavedLanguage(context) // Lấy ngôn ngữ hiện tại
                    val newLanguage = if (currentLanguage == "en") "ja" else "en" // Đổi ngôn ngữ

                    LocaleHelper.setLocale(context, newLanguage) // Áp dụng ngôn ngữ mới
                    LocaleHelper.restartActivity(context as Activity)
                          },
                modifier = Modifier
                    .padding(end = paddingWidth),
                buttonWidthFactor = 0.15f,
                buttonHeightFactor = 0.06f,
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
                fontSizeFactor = 0.02f
            )
        }

    }
}


