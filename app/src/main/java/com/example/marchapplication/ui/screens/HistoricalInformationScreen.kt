package com.example.marchapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.marchapplication.ui.components.ButtonCustom
import com.example.marchapplication.R
import com.example.marchapplication.ViewModel.HistoricalInformationViewModel
import com.example.marchapplication.ui.components.ButtonIconPlay
import com.example.marchapplication.ui.components.TextCustom

@Composable
fun HistoricalInformationScreen(
    navController: NavController,
    folderName: String,
    viewModel: HistoricalInformationViewModel
) {
    val avatarResId by viewModel.avatarResId.collectAsState()
    val textEN by viewModel.textEN.collectAsState()
    val textJP by viewModel.textJP.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val isPaused by viewModel.isPaused.collectAsState()

    LaunchedEffect(folderName) {
        viewModel.loadCarData(folderName)
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopReading()
        }
    }
    val imageRes = avatarResId ?: R.drawable.defaults

    val configuration = LocalConfiguration.current
    val paddingHeight = configuration.screenHeightDp.dp * 0.02f
    val paddingWidth = configuration.screenWidthDp.dp * 0.06f
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
                .background(Color.White)
                .padding(top = paddingHeight),
            horizontalAlignment = Alignment.Start
        ) {
            TextCustom(
                text = stringResource(id = R.string.history),
                modifier = Modifier
                    .padding(start = paddingWidth)
                    .testTag("HistoryText"),
                fontSizeFactor = 0.03f
            )

            Box(
                modifier = Modifier
                    .weight(3f)
                    .padding(top = paddingHeight, end = paddingHeight, start = paddingWidth)
                    .border(1.dp, Color.Gray)
                    .clip(RectangleShape)
                    .testTag("AvatarImage")
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = paddingHeight, bottom = paddingHeight),
                contentAlignment = Alignment.Center
            ) {
                TextCustom(
                    text = folderName,
                    modifier = Modifier.testTag("FolderNameText")
                )
            }
            ButtonCustom(
                text = "Back",
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(start = paddingWidth, bottom = paddingHeight)
                    .testTag("BackButton"),
                buttonWidthFactor = 0.15f,
                buttonHeightFactor = 0.06f,
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black,
            )
        }
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.End
        ) {
            ButtonIconPlay(
                isPlaying = isPlaying,
                isPaused = isPaused,
                onClick = {
                    if (isPlaying) {
                        viewModel.stopReading()
                    } else {
                        viewModel.speakText()
                    }
                },
                modifier = Modifier
                    .padding(end = paddingWidth, top = paddingHeight)
                    .testTag("PlayButton")
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = paddingHeight, end = paddingWidth, top = paddingHeight, bottom = paddingHeight)
                    .verticalScroll(rememberScrollState())
                    .testTag("ContentText")
            ) {
                TextCustom(
                    text = if (currentLanguage == "en") textEN else textJP,
                    fontSizeFactor = 0.015f,
                )
            }
        }
    }
}

