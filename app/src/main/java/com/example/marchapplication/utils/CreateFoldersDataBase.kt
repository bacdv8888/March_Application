package com.example.marchapplication.utils

import android.content.Context
import android.content.SharedPreferences

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

