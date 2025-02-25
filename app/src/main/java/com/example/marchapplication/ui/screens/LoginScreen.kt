package com.example.marchapplication.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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
import com.example.marchapplication.utils.signIn
import com.example.marchapplication.ui.components.ButtonCustom
import com.example.marchapplication.ui.components.TextFieldWithIcon
import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(navController: NavController) {
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding()
            .background(
                color = Color(0xFFFFFFFF)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Sign In", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(text = "Wellcome to the App", fontSize = 20.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(10.dp))
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
        Row (){
            ButtonCustom(
                text = "Login",
                onClick = {
                    if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                        signIn(email.value, password.value,
                            onSuccess = {
                                Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                                navController.navigate("homeScreen")
                            },
                            onError = { error ->
                                Toast.makeText(context, "Lỗi: $error", Toast.LENGTH_LONG).show()
                                Log.e("LoginScreen", "Lỗi: $error")
                            }
                        )
                    } else {
                        Toast.makeText(context, "Email và mật khẩu không được để trống!", Toast.LENGTH_SHORT).show()
                    }
                },
                backgroundColor = Color(0xFFE0ECF7),
                modifier = Modifier
                    .size(250.dp, 80.dp),
                cornerRadius = 10.dp,
                textColor = Color.Black
            )
            ButtonCustom(
                text = "Register",
                onClick = { navController.navigate("createAccountScreen") },
                modifier = Modifier
                    .size(250.dp, 80.dp),
                backgroundColor = Color(0xFFE0ECF7),
                textColor = Color.Black
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}