package com.example.marchapplication.ui.screens


import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marchapplication.Data.AppDatabase
import com.example.marchapplication.Data.Photo
import com.example.marchapplication.R
import com.example.marchapplication.utils.shareImage
import com.example.marchapplication.ui.components.ButtonCustom
import com.example.marchapplication.ui.components.CustomTextField
import com.example.marchapplication.ui.components.TextCustom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun InformationCarScreen(navController: NavController, imagePath: String?) {
    val folderName = imagePath?.split("/")?.getOrNull(imagePath.split("/").size-2)?:"Unknow"

    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val photoDao = remember { database.photoDao() }
    val photoData = remember { mutableStateOf<Photo?>(null) }

    // Truy vấn database để lấy thông tin ảnh
    LaunchedEffect(imagePath) {
        if (imagePath != null) {
            val photo = withContext(Dispatchers.IO) {
                photoDao.getPhotoByPath(imagePath)
            }
            photoData.value = photo
        }
    }

    // Trạng thái lưu trữ giá trị có thể chỉnh sủa
    val carName = remember { mutableStateOf(photoData.value?.CarName ?: "") }
    val datecaptured = remember { mutableStateOf(photoData.value?.DateCaptured ?: "") }
    val location = remember { mutableStateOf(photoData.value?.Location ?: "") }
    val capturedBy = remember { mutableStateOf(photoData.value?.CapturedBy ?: "") }

    val configuration = LocalConfiguration.current
    val PaddingHeight = configuration.screenHeightDp.dp * 0.01f
    val PaddingWidth = configuration.screenWidthDp.dp * 0.05f

    LaunchedEffect(photoData.value) {
        photoData.value?.let {
            carName.value= it.CarName
            location.value= it.Location ?: ""
            capturedBy.value= it.CapturedBy ?: ""
            datecaptured.value= it.DateCaptured?: ""
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .background(Color.White)
                .padding(top = PaddingHeight),
        ) {
            TextCustom(
                text = stringResource(id = R.string.details),
                modifier = Modifier.
                padding(start = PaddingWidth),
                fontSizeFactor = 0.04f
                //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(PaddingHeight))
            Row(
                modifier = Modifier
                    //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth(1f)
                    .padding(start = PaddingWidth)
                    .weight(2f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .border(2.dp, Color.Gray)
                        .fillMaxWidth(0.5f)
                        .clip(RectangleShape)

                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imagePath)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }

                Column(
                    modifier = Modifier
                        .background(Color.White),
                        //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp)),
                    verticalArrangement = Arrangement.Center
                ){
                    CustomTextField(label = "車種名:", value = carName)
                    CustomTextField(label = "撮影日:", value = datecaptured)
                    CustomTextField(label = "撮影場所:", value = location)
                    CustomTextField(label = "オーナー:", value = capturedBy)

                }
            }
            Spacer(modifier = Modifier.height(PaddingHeight))
            ButtonCustom(
                text = "Save",
                onClick = {
                    if (photoData.value != null) {
                        val updatedPhoto = photoData.value!!.copy(
                            CarName = carName.value,
                            Location = location.value,
                            CapturedBy = capturedBy.value
                        )
                        // Cập nhật database
                        GlobalScope.launch(Dispatchers.IO) {
                            photoDao.updatePhoto(updatedPhoto)
                        }
                        Toast.makeText(context, "Save To DataBase", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .padding(start = PaddingWidth, bottom = PaddingHeight),
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
            )
        }
        Column(
            modifier = Modifier
                //.border(2.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White)
                .padding(top = PaddingHeight),
            horizontalAlignment = Alignment.End
        ) {
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
            Spacer(modifier = Modifier.weight(1f))
            ButtonCustom(
                text = "History",
                onClick = { navController.navigate("historicalInformationScreen/${Uri.encode(folderName)}") },
                modifier = Modifier
                    .padding(end = PaddingWidth, bottom = PaddingHeight),
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
                )
            ButtonCustom(
                text = "Share",
                onClick = {
                    if (imagePath != null) {
                        shareImage(context, imagePath, carName.value, datecaptured.value, location.value, capturedBy.value)
                    } else {
                        Toast.makeText(context, "No Image!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .padding(end = PaddingWidth, bottom = PaddingHeight),
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
                )
        }
    }
}
