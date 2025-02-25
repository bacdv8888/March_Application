package com.example.marchapplication.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.marchapplication.ui.components.ButtonIcon

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marchapplication.Data.AppDatabase
import com.example.marchapplication.Data.Photo
import com.example.marchapplication.R
import com.example.marchapplication.ui.components.ButtonCustom
import com.example.marchapplication.ui.components.TextCustom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun ListCarImageScreen(
    navController: NavController,
    folderName: String
) {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val photoDao = remember { database.photoDao() }

    val imageList = remember { mutableStateOf(emptyList<Photo>()) }

    val configuration = LocalConfiguration.current
    val PaddingHeight = configuration.screenHeightDp.dp * 0.02f
    val PaddingWidth = configuration.screenWidthDp.dp * 0.05f

    LaunchedEffect(Unit) {
        val images = withContext(Dispatchers.IO) {
            photoDao.getImagesByCar(folderName)
        }
        imageList.value = images
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = PaddingHeight),
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
                    .padding(start = PaddingWidth),
                fontSizeFactor = 0.03f
            )
            ButtonCustom(
                text = "Back",
                onClick = { navController.popBackStack()},
                modifier = Modifier
                    .padding(end = PaddingWidth),
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
            if (imageList.value.isEmpty()) {
            Box(modifier = Modifier
                .weight(5f)
                .fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
                Text(text = "No images found for '$folderName'.")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = PaddingWidth),
                contentPadding = PaddingValues(5.dp)
            ) {
                items(imageList.value) { photo ->
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
                .padding(4.dp)
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
                    //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        if (displayTime.isNotEmpty()) {
            TextCustom(
                text = "$displayTime",
                modifier = Modifier
                    .padding(3.dp),
                        //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
            )
        }
    }
}

fun isImageFile(file: File): Boolean {
    val name = file.name.lowercase()
    return (name.endsWith(".jpg")
            || name.endsWith(".jpeg")
            || name.endsWith(".png")
            || name.endsWith(".bmp") || name.endsWith(".webp"))
}