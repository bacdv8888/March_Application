package com.example.marchapplication.ui.screens

import android.Manifest
import android.net.Uri
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.marchapplication.utils.saveImageToAppFolder
import com.example.marchapplication.ui.components.ButtonCustom
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.marchapplication.Data.AppDatabase
import com.example.marchapplication.R
import com.example.marchapplication.ui.components.TextCustom
import com.example.marchapplication.utils.CAR_LIST
import com.example.marchapplication.utils.LocationHelper

import com.example.marchapplication.utils.launchCamera
import com.example.marchapplication.utils.rememberCameraState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelSettingScreen(
    navController: NavController,
    imageUri: Uri?){

    val expanded = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf(CAR_LIST[0]) }
    val context = LocalContext.current
    val cameraState = rememberCameraState(navController)

    // Lấy database instance
    val database = remember { AppDatabase.getDatabase(context) }
    val photoDao = remember { database.photoDao() }

    val isSaving = remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val paddingHeight = configuration.screenHeightDp.dp * 0.02f
    val paddingWidth = configuration.screenWidthDp.dp * 0.06f
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = paddingHeight),
    ) {
        TextCustom(
            text = stringResource(id = R.string.car_selection),
            modifier = Modifier.padding(start = paddingWidth),
            fontSizeFactor = 0.03f
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            ExposedDropdownMenuBox(
                expanded = expanded.value,
                onExpandedChange = { expanded.value = !expanded.value }
            ) {
                TextField(
                    value = selectedItem.value,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                    modifier = Modifier
                        .menuAnchor()
                        .border(2.dp, Color.Gray)
                )
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier
                        .border(2.dp, Color.Gray)
                        .size(280.dp,300.dp)
                ) {
                    CAR_LIST.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = item,
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                            },
                            onClick = {
                                selectedItem.value = item
                                expanded.value = false
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(2f))
        Row(
            modifier = Modifier
                //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .padding(bottom = paddingHeight),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            ButtonCustom(
                text = "Cancel",
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
                text = "OK",
                onClick = {
                    if (!isSaving.value) {
                        isSaving.value = true
                        imageUri?.let { uri ->
                            val selectedFolder = selectedItem.value
                                saveImageToAppFolder(context, uri, selectedFolder, photoDao)
                        }
                        if (!LocationHelper.hasLocationPermission(context)) {
                            val toast = Toast.makeText(
                                context,
                                "Image cannot be geotagged because location permission is not granted",
                                Toast.LENGTH_SHORT
                            )
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                        navController.navigate("homeScreen")
                        Toast.makeText(context, "Add To Car List ", Toast.LENGTH_SHORT).show()
                        }
                },
                enabled = !isSaving.value,
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
            )
        }
    }
}