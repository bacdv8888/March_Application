package com.example.marchapplication.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.marchapplication.Data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

fun createImageFolders(context: Context) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

    if (isFirstRun) {
        for (folderName in CAR_LIST) {
            val folder = File(context.filesDir, "Images/$folderName")
            if (!folder.exists()) folder.mkdirs()
        }

        sharedPreferences.edit().putBoolean("isFirstRun", false).apply()


        val baseDir = File(context.filesDir, "Images")
        if (!baseDir.exists()) baseDir.mkdirs()

        for (name in CAR_LIST) {
            val folder = File(baseDir, name)
            if (!folder.exists()) folder.mkdirs()
        }
        sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
    }
}

fun initializeDatabase(context: Context) {
    val database = AppDatabase.getDatabase(context)
    val photoDao = database.photoDao()

    GlobalScope.launch(Dispatchers.IO) {
        val count = photoDao.getAllPhotos().size
        if (count == 0) {
            Log.d("Database", "Database chưa có ảnh nào, sẽ chờ ảnh mới được thêm vào.")
        }
    }
}

