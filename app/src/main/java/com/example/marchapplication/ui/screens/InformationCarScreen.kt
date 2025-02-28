package com.example.marchapplication.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marchapplication.R
import com.example.marchapplication.ViewModel.CarViewModel
import com.example.marchapplication.utils.shareImage
import com.example.marchapplication.ui.components.ButtonCustom
import com.example.marchapplication.ui.components.CustomTextField
import com.example.marchapplication.ui.components.TextCustom

@Composable
fun InformationCarScreen(
    navController: NavController,
    imagePath: String?,
    viewModel: CarViewModel
) {
    val folderName = imagePath?.split("/")?.getOrNull(imagePath.split("/").size - 2) ?: "Unknown"
    val context = LocalContext.current
    val carName by viewModel.carName.collectAsState()
    val dateCaptured by viewModel.dateCaptured.collectAsState()
    val location by viewModel.location.collectAsState()
    val capturedBy by viewModel.capturedBy.collectAsState()

    LaunchedEffect(imagePath) {
        if (imagePath != null) {
            viewModel.loadPhotoData(imagePath)
        }
    }

    val configuration = LocalConfiguration.current
    val paddingHeight = configuration.screenHeightDp.dp * 0.01f
    val paddingWidth = configuration.screenWidthDp.dp * 0.06f
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        Column(
            modifier = Modifier
                .weight(4f)
                .fillMaxHeight()
                .background(Color.White)
                .padding(top = paddingHeight),
        ) {
            TextCustom(
                text = stringResource(id = R.string.details),
                modifier = Modifier.
                padding(start = paddingWidth, bottom = paddingHeight),
                fontSizeFactor = 0.03f
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(start = paddingWidth)
                    .weight(2f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(RectangleShape)
                        .padding(end = 20.dp, bottom = paddingHeight),

                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imagePath)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
                Spacer(modifier = Modifier.height(paddingHeight))
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .background(Color.White),
                    verticalArrangement = Arrangement.Center
                ){
                    CustomTextField(label = "車種名:", value = carName, onValueChange = { viewModel.updateCarName(it) })
                    CustomTextField(label = "撮影日:", value = dateCaptured)
                    CustomTextField(label = "撮影場所:", value = location, onValueChange = { viewModel.updateLocation(it) })
                    CustomTextField(label = "オーナー:", value = capturedBy, onValueChange = { viewModel.updateCapturedBy(it) })

                }
            }
            ButtonCustom(
                text = "Save",
                onClick = {
                    viewModel.updatePhotoData()
                    Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .padding(start = paddingWidth, bottom = paddingHeight),
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White)
                .padding(top = paddingHeight),
            horizontalAlignment = Alignment.End
        ) {
            ButtonCustom(
                text = "Back",
                onClick = { navController.popBackStack()},
                modifier = Modifier
                    .padding(end = paddingWidth),
                buttonWidthFactor = 0.15f,
                buttonHeightFactor = 0.06f,
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
            )
            Spacer(modifier = Modifier.weight(1f))
            ButtonCustom(
                text = "History",
                onClick = { navController.navigate("historicalInformationScreen/${Uri.encode(folderName)}") },
                modifier = Modifier
                    .padding(end = paddingWidth, bottom = paddingHeight),
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
                )
            ButtonCustom(
                text = "Share",
                onClick = {
                    if (imagePath != null) {
                        shareImage(context, imagePath, carName, dateCaptured, location, capturedBy)
                    } else {
                        Toast.makeText(context, "No Image!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .padding(end = paddingWidth, bottom = paddingHeight),
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
                )
        }
    }
}
