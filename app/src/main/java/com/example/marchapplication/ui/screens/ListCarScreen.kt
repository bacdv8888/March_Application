package com.example.marchapplication.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.marchapplication.ui.components.ButtonCustom
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.layout.ContentScale
import com.example.marchapplication.R
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.marchapplication.Data.AppDatabase
import com.example.marchapplication.ui.components.TextCustom
import com.example.marchapplication.utils.CAR_LIST

@Composable
fun ListCarScreen(navController: NavController) {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val photoDao = remember { database.photoDao() }
    val configuration = LocalConfiguration.current
    val paddingHeight = configuration.screenHeightDp.dp * 0.02f
    val paddingWidth = configuration.screenWidthDp.dp * 0.06f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = paddingHeight)
            .testTag("ListCarScreen")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = paddingWidth, bottom = paddingHeight, end = paddingWidth),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextCustom(
                text = stringResource(id = R.string.car_list),
                modifier = Modifier
                    .padding(top = paddingHeight)
                    .testTag("CarListText"),
                fontSizeFactor = 0.03f
            )
            ButtonCustom(
                text = "Back",
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding()
                    .testTag("BackButton"),
                buttonWidthFactor = 0.15f,
                buttonHeightFactor = 0.06f,
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = paddingWidth, end = paddingWidth, bottom = paddingHeight)
                .testTag("CarGrid"),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(CAR_LIST) { carName ->
                val avatarResId by photoDao.getAvatarForCar(carName).collectAsState(initial = null)
                val imageCount by photoDao.getImageCountForCar(carName).collectAsState(initial = 0)
                CarGridItem(
                    folderName = carName,
                    avatarResId = avatarResId ?: R.drawable.defaults,
                    imageCount = imageCount,
                    onClick = {
                        val encodedName = Uri.encode(carName)
                        navController.navigate("listCarImageScreen/$encodedName")
                    }
                )
            }
        }
    }
}

@Composable
fun CarGridItem(
    folderName: String,
    avatarResId: Int,
    imageCount: Int,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.testTag("CarGridItem")
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clickable { onClick() }
                .testTag("CarImageBox")
        ) {
            Image(
                painter = painterResource(id = avatarResId),
                contentDescription = null,
                modifier = Modifier.matchParentSize().testTag("CarImage"),
                contentScale = ContentScale.Fit
            )
        }
        TextCustom(
            text = "$folderName ($imageCount Image)",
            modifier = Modifier
                .padding(10.dp)
                .testTag("CarImageText"),
            fontSizeFactor = 0.015f
        )
    }
}