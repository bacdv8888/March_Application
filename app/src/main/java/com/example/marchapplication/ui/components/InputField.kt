package com.example.marchapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextFieldWithIcon(
    label: String,
    icon: ImageVector,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.size(400.dp,60.dp),
    ) {
        Text(text = label,
            fontSize = 16.sp,
            color = Color.Black,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(20.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
        }
    }
}
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
            onValueChange = onValueChange, // ✅ Sử dụng tham số mới
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


/*
@Composable
fun CustomTextField(
    label: String,
    value: MutableState<String> // ✅ Yêu cầu MutableState<String>
) {
    TextField(
        value = value.value, // ✅ Đọc giá trị từ MutableState
        onValueChange = { value.value = it }, // ✅ Cập nhật giá trị mới
        label = { Text(label, fontSize = 16.sp, color = Color.Gray) },
        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF5F5F5),
            unfocusedContainerColor = Color(0xFFF5F5F5),
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Color.Blue
        )
    )
}

 */



