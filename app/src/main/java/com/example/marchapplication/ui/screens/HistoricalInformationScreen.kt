package com.example.marchapplication.ui.screens

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.marchapplication.ui.components.ButtonCustom
import com.example.marchapplication.R
import com.example.marchapplication.ui.components.ButtonIconPlay
import com.yourapp.utils.LocaleHelper

@Composable
fun HistoricalInformationScreen(
    navController: NavController,
    folderName: String,
    viewModel: HistoricalInformationViewModel
) {
    val avatarResId by viewModel.avatarResId.collectAsState()
    val textEN by viewModel.textEN.collectAsState()
    val textJP by viewModel.textJP.collectAsState()
    val context = LocalContext.current
    val currentLanguage = LocaleHelper.getSavedLanguage(context)
    val textHistory = if (currentLanguage == "en") textEN else textJP

    LaunchedEffect(folderName) {
        viewModel.loadCarData(folderName)
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopReading()
        }
    }

    val imageRes = avatarResId ?: R.drawable.defaults
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
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.history),
                fontSize = 35.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 60.dp)
            )
            Box(
                modifier = Modifier
                    .weight(3f)
                    .padding(top = 10.dp, end = 20.dp, bottom = 5.dp, start = 60.dp)
                    .border(2.dp, Color.Gray)
                    .clip(RectangleShape)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = folderName,
                    fontSize = 22.sp,
                    color = Color.Black
                )
            }
            ButtonCustom(
                text = "Back",
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(start = 60.dp, bottom = 10.dp),
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
                .background(Color.White)
                .padding(top = 5.dp),
            horizontalAlignment = Alignment.End
        ) {
            ButtonIconPlay(
                onClick = { viewModel.speakText(textHistory) },
                modifier = Modifier
                    .padding(end = 60.dp)
            )
            Box(
                modifier = Modifier
                    .padding( end = 40.dp, top = 10.dp, bottom = 10.dp)
                    .verticalScroll(rememberScrollState())
                    .border(2.dp, Color.Gray)
            ) {
                Text(
                    text = textHistory,
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}