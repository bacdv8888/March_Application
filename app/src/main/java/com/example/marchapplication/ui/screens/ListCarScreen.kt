package com.example.marchapplication.ui.screens

import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.marchapplication.Data.AppDatabase
import com.example.marchapplication.ui.components.TextCustom
import com.example.marchapplication.utils.CAR_LIST

@Composable
fun ListCarScreen(navController: NavController) {
    val context = LocalContext.current

    val database = remember { AppDatabase.getDatabase(context) }
    val photoDao = remember { database.photoDao() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextCustom(
                text = stringResource(id = R.string.car_list),
                modifier = Modifier.padding(start = 20.dp),
                fontSizeFactor = 0.04f
            )

            ButtonCustom(
                text = "Back",
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(end = 20.dp),
                buttonWidthFactor = 0.15f,
                buttonHeightFactor = 0.06f,
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(CAR_LIST) { carName ->
                // Lấy ảnh từ database

                val avatarResId by photoDao.getAvatarForCar(carName).collectAsState(initial = null)
                val imageCount by photoDao.getImageCountForCar(carName).collectAsState(initial = 0)

                CarGridItem(
                    folderName = carName,
                    avatarResId = avatarResId ?: R.drawable.defaults, // Nếu không có ảnh, dùng ảnh mặc định
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
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
    ) {
        Image(
            painter = painterResource(id = avatarResId), // Hiển thị ảnh từ drawable
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Fit
        )

        TextCustom(
            text = "$folderName ($imageCount Image)",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp),
        )
    }
}







/*
@Composable
fun ListCarScreen(navController: NavController) {

    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val PaddingHeight = configuration.screenHeightDp.dp * 0.02f
    val PaddingWidth = configuration.screenWidthDp.dp * 0.05f
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 20.dp)
    ) {
        Row(
            modifier = Modifier
                //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            ->
            TextCustom(
                text = "Car List",
                modifier = Modifier.
                padding(start = PaddingWidth),
                fontSizeFactor = 0.04f
                //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = PaddingWidth),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(carAlbumList) { album ->
                // 1) Lấy thư mục Images/<folderName>
                val folder = File(context.filesDir, "Images/${album.carName}")
                // 2) Lọc ra file ảnh
                val listImages = folder.listFiles()?.filter {
                    it.isFile && it.canRead() && isImageFile(it)
                } ?: emptyList()
                // 3) Kiểm tra thư mục này có rỗng hay không
                val isEmptyAlbum = listImages.isEmpty()
                val imageCount = listImages.size
                CarGridItem(
                    album = album,
                    isEmptyAlbum = isEmptyAlbum,
                    imageCount = imageCount,
                    onClick = {
                        val encodedName = Uri.encode(album.carName)
                        navController.navigate("listCarImageScreen/$encodedName")
                    }
                )
            }
        }

    }
}
@Composable
fun CarGridItem(
    album: CarAlbum,
    isEmptyAlbum: Boolean,
    imageCount: Int,
    onClick: () -> Unit
) {
    val iconToShow = if (isEmptyAlbum) {
        R.drawable.defaults
    } else {
        album.imageResId
    }
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
    ) {
        Image(
            painter = painterResource(id = iconToShow),
            contentDescription = null,
            modifier = Modifier
                //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
                .matchParentSize(),
            contentScale = ContentScale.Fit
        )

        // Hiển thị tên album ở góc dưới
        TextCustom(
            text = "${album.carName} ($imageCount Image)\"",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp),
        )
    }
}
// Hiển thị ảnh trực tiếp
/*
items(carAlbumList) { album ->
    CarGridItem(
        album = album,
        onClick = {
            val encodedName = Uri.encode(album.folderName)
            navController.navigate("listCarImageScreen/$encodedName")
        }
    )
}

*/
/*
@Composable
fun CarGridItem(
    album: CarAlbum,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
    ) {
        // Hiển thị icon album
        Image(
            painter = painterResource(id = album.iconRes),
            contentDescription = null,
            modifier = Modifier
                .border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
                .matchParentSize(),
            contentScale = ContentScale.Fit
        )

        Text(
            text = album.folderName,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(2.dp)
        )
    }
}
*/