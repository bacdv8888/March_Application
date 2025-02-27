package com.example.marchapplication

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.marchapplication.Navigation.AppNavigator
import com.example.marchapplication.utils.createImageFolders
import com.example.marchapplication.ui.theme.MarchApplicationTheme
import com.example.marchapplication.utils.LocaleHelper
import com.example.marchapplication.utils.LocationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Tạo thư mục ảnh và database khi chạy ứng dụng lần đầu tiên
        GlobalScope.launch(Dispatchers.IO) {
            createImageFolders(this@MainActivity)
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
        if (!LocationHelper.hasLocationPermission(this)) {
            LocationHelper.requestLocationPermission(this)
        } else {
            LocationHelper.fetchLocation(this) { location ->
            }
        }
    }
    override fun attachBaseContext(newBase: Context) {
        val locale = LocaleHelper.getSavedLanguage(newBase)
        val context = LocaleHelper.setLocale(newBase, locale)
        super.attachBaseContext(context)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LocationHelper.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                LocationHelper.fetchLocation(this) { location ->}
            } else {
                Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
