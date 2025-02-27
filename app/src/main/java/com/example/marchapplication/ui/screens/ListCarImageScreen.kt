package com.example.marchapplication.ui.screens

import android.net.Uri
import androidx.compose.foundation.background

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController


import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marchapplication.R
import com.example.marchapplication.ViewModel.CarViewModel
import com.example.marchapplication.ui.components.ButtonCustom
import com.example.marchapplication.ui.components.TextCustom

import java.io.File

@Composable
fun ListCarImageScreen(
    navController: NavController,
    folderName: String,
    viewModel: CarViewModel
) {
    val imageList by viewModel.imageList.collectAsState()

    LaunchedEffect(folderName) {
        viewModel.loadImagesByCar(folderName)
    }
    val configuration = LocalConfiguration.current
    val paddingHeight = configuration.screenHeightDp.dp * 0.02f
    val paddingWidth = configuration.screenWidthDp.dp * 0.06f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = paddingHeight),
    ) {
        // Thanh tiêu đề
        Row(
            modifier = Modifier
                //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextCustom(
                text = "$folderName " + stringResource(id = R.string.catch_list),
                modifier = Modifier
                    .padding(start = paddingWidth),
                fontSizeFactor = 0.03f
            )
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
        }
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            if (imageList.isEmpty()) {
            Box(modifier = Modifier
                .weight(5f)
                .fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
                Text(text = "No images found for '$folderName'.")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = paddingWidth, end = paddingWidth, bottom = paddingHeight),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(imageList) { photo ->
                    ImageGridItemWithTime(
                        imagePath = photo.FilePath,
                        displayTime = photo.DateCaptured
                    ) { path, time ->
                        navController.navigate("informationCarScreen/${Uri.encode(path)}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImageGridItemWithTime(
    imagePath: String,
    displayTime: String,
    onClick: (String, String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
                .padding(10.dp)
                .fillMaxSize()
                .aspectRatio(1f)
                .clickable { onClick(imagePath, displayTime)
                }
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(File(imagePath)) // Load ảnh từ database
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        if (displayTime.isNotEmpty()) {
            TextCustom(
                text = displayTime.toString(),
                        modifier = Modifier
                    .padding(3.dp),
                fontSizeFactor = 0.015f
            )
        }
    }
}
