package com.example.marchapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit = {} // ✅ Thêm tham số onValueChange mặc định
) {
    val configuration = LocalConfiguration.current
    val adaptiveFontSize = (configuration.screenWidthDp * 0.02).sp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = adaptiveFontSize,
            color = Color.Black,
            modifier = Modifier.padding(end = 8.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = adaptiveFontSize, color = Color.Black),
            modifier = Modifier
                .weight(1f)
                .height(IntrinsicSize.Min),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.White,
                cursorColor = Color.Black
            )
        )
    }
}



