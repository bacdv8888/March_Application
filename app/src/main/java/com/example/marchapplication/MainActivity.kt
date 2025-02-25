package com.example.marchapplication

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.marchapplication.Navigation.AppNavigator
import com.example.marchapplication.utils.createImageFolders
import com.example.marchapplication.utils.initializeDatabase
import com.example.marchapplication.ui.theme.MarchApplicationTheme
import com.google.firebase.FirebaseApp
import com.yourapp.utils.LocaleHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        // Tạo thư mục ảnh và database khi chạy ứng dụng lần đầu tiên
        GlobalScope.launch(Dispatchers.IO) {
            createImageFolders(this@MainActivity)
            initializeDatabase(this@MainActivity)
        }

        LocaleHelper.setLocale(this, LocaleHelper.getSavedLanguage(this))

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        enableEdgeToEdge()
        setContent {
            MarchApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigator(Modifier.fillMaxSize().padding(innerPadding))
                }
            }
        }
    }
}
