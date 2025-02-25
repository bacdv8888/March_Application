package com.example.marchapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.marchapplication.ui.components.ButtonCustom
import com.example.marchapplication.ui.components.ButtonIcon
import com.example.marchapplication.ui.components.TextFieldWithIcon

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.marchapplication.utils.createAccount

@Composable
fun CreateAccountScreen(navController: NavController) {
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var confirm_password = remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .background(
                color = Color(0xFFFFFFFF)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .size(460.dp, 50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonIcon(
                icon = Icons.Default.ArrowBack,
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(start = 10.dp)

            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Create Account!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.weight(2f))
        }
        Text(text = "Welcome to the App", fontSize = 20.sp, color = Color.Black)

        TextFieldWithIcon(
            label = "Email address",
            icon = Icons.Default.Email,
            value = email.value,
            onValueChange = { email.value = it },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        TextFieldWithIcon(
            label = "Password",
            icon = Icons.Default.Lock,
            value = password.value,
            onValueChange = { password.value = it },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        TextFieldWithIcon(
            label = "Confirm password",
            icon = Icons.Default.Lock,
            value = confirm_password.value,
            onValueChange = { confirm_password.value = it },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        ButtonCustom(
            text = "Sign Up",
            onClick = {
                if (email.value.isEmpty() || password.value.isEmpty() || confirm_password.value.isEmpty()) {
                    Toast.makeText(context, "Email và mật khẩu không được để trống!", Toast.LENGTH_SHORT).show()
                } else if (password.value != confirm_password.value) {
                    Toast.makeText(context, "Mật khẩu không khớp! Vui lòng nhập lại.", Toast.LENGTH_SHORT).show()
                } else {
                    createAccount(email.value, password.value,
                        onSuccess = {
                            Toast.makeText(context, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show()
                            navController.navigate("loginScreen")
                        },
                        onError = { error ->
                            Toast.makeText(context, "Lỗi: $error", Toast.LENGTH_LONG).show()
                            Log.e("CreateAccount", "Lỗi: $error")
                        }
                    )
                }
            },
            modifier = Modifier
                .size(250.dp, 80.dp),
            backgroundColor = Color(0xFFE0ECF7),
            cornerRadius = 10.dp,
            textColor = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}