package com.example.marchapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonCustom(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF007AFF),
    cornerRadius: Dp = 0.dp,
    textColor: Color = Color.White,
    buttonWidthFactor: Float = 0.15f,
    buttonHeightFactor: Float = 0.06f,
    //buttonWidth : Dp = 100.dp,
    //buttonHeight: Dp = 60.dp,
    fontSizeFactor: Float = 0.025f
    //fontSize: TextUnit = 15.sp
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    //val configuration = LocalConfiguration.current
    //val buttonWidth = configuration.screenWidthDp.dp * buttonWidthFactor
    //val buttonHeight = configuration.screenHeightDp.dp * buttonHeightFactor

    val buttonWidth = (screenWidth * buttonWidthFactor).dp.coerceAtLeast(120.dp)
    val buttonHeight = (screenWidth * buttonHeightFactor).dp.coerceAtLeast(48.dp)
    val adaptiveFontSize = (screenWidth * fontSizeFactor).sp.value.coerceAtLeast(16f).sp


    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(cornerRadius),
        modifier = modifier
            .width(buttonWidth)
            .height(buttonHeight)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = adaptiveFontSize
        )
    }
}
@Composable
fun TextCustom(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    fontSizeFactor: Float = 0.02f,
    //fontSize: TextUnit = 20.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start
) {
    val configuration = LocalConfiguration.current
    val adaptiveFontSize = (configuration.screenWidthDp * fontSizeFactor).sp
    Text(
        text = text,
        color = textColor,
        fontSize = adaptiveFontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        modifier = modifier
    )
}


@Composable
fun ButtonIcon(
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(50.dp)
            .background(Color.White, shape = CircleShape)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Back",
            tint = Color.Black,
        )
    }
}

@Composable
fun ButtonIconPlay(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(50.dp)
            .border(2.dp, Color.Black, shape = CircleShape) // Add circular border
            .background(Color.White, shape = CircleShape)
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Play",
            tint = Color.Black,
            modifier = Modifier.size(40.dp)
        )
    }
}