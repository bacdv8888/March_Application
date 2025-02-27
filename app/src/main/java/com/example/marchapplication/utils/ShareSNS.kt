package com.example.marchapplication.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

fun shareImage(context: Context, imagePath: String, carName: String, dateCaptured: String, location: String, capturedBy: String) {
    val file = File(imagePath)

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
    // Tạo nội dung văn bản đi kèm
    val imageInfo = """
        車種名: $carName
        撮影日: $dateCaptured
        撮影場所: $location
        オーナー: $capturedBy
    """.trimIndent()
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "image/*"
        putExtra(Intent.EXTRA_STREAM, uri)
        putExtra(Intent.EXTRA_TEXT, imageInfo)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share SNS"))
}
