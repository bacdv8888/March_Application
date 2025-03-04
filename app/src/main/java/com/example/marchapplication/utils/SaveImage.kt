package com.example.marchapplication.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import com.example.marchapplication.Data.Photo
import com.example.marchapplication.Data.PhotoDao
import com.example.marchapplication.R
import com.example.marchapplication.utils.LocationHelper.getDetailedAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun saveImageToAppFolder(
    context: Context,
    imageUri: Uri,
    folderName: String,
    photoDao: PhotoDao, ) {
    try {

        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        // Read EXIF information from the original image (if available)
        val exifInputStream = context.contentResolver.openInputStream(imageUri)
        val exif = exifInputStream?.let { ExifInterface(it) }
        exifInputStream?.close()

        val rotationAngle = when (exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            else -> 0f
        }

        // Rotate the image if necessary
        val rotatedBitmap = if (rotationAngle != 0f) {
            rotateBitmap(bitmap, rotationAngle)
        } else {
            bitmap
        }


        val baseDir = File(context.filesDir, "Images/$folderName")
        if (!baseDir.exists()) baseDir.mkdirs()

        val file = File(baseDir, "IMG_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()

        val dateString = SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.getDefault()).format(Date())
        val newExif = ExifInterface(file.path)
        newExif.setAttribute(ExifInterface.TAG_DATETIME, dateString)
        newExif.setAttribute(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL.toString())
        newExif.saveAttributes()
        val avatarResId = getAvatarForFolder(folderName)

        // Find the CarInfo object for the given folderName
        val carInfo = carInfoList.find { it.carName == folderName }
        val textEN = carInfo?.textEN ?: ""
        val textJP = carInfo?.textJP ?: ""
        val imagePath = file.absolutePath
        println("Image saved to $imagePath with EXIF TAG_DATETIME = $dateString")

        // Save to Room database
        LocationHelper.fetchLocation(context) { location ->
            val locationString = if (location != null) {
                val coordinates = "${location.latitude},${location.longitude}"
                Log.d("Location", coordinates)
                val detailedAddress = getDetailedAddress(context, location.latitude, location.longitude)
                if (!detailedAddress.isNullOrEmpty()) {
                    detailedAddress
                } else {
                    "${location.latitude},${location.longitude}"
                }
            } else {
                "Unknown"
            }
            GlobalScope.launch(Dispatchers.IO) {
                val photo = Photo(
                    NameProduct =folderName,
                    FilePath = imagePath,
                    CarName = folderName,
                    Image = avatarResId,
                    CapturedBy = "",
                    DateCaptured = dateString,
                    Location = locationString,
                    TextEN = textEN,
                    TextJP = textJP,
                )
                photoDao.insertPhoto(photo)
            }
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}
// Hàm lấy đường dẫn ảnh đại diện dựa vào folderName
fun getAvatarForFolder(folderName: String): Int {
    val carAlbumList = listOf(
        CarAlbum("CX-90", R.drawable.a_image),
        CarAlbum("CX-80", R.drawable.b_image),
        CarAlbum("CX-8", R.drawable.c_image),
        CarAlbum("CX-70", R.drawable.d_image),
        CarAlbum("CX-60", R.drawable.e_image),
        CarAlbum("CX-50", R.drawable.f_image),
        CarAlbum("CX-5", R.drawable.g_image),
        CarAlbum("CX-30", R.drawable.h_image),
        CarAlbum("CX-3", R.drawable.i_image),
        CarAlbum("BT-50", R.drawable.k_image),
        CarAlbum("MX-5", R.drawable.l_image),
        CarAlbum("MX-30", R.drawable.m_image)
    )
    val carAlbum = carAlbumList.find { it.carName == folderName }
    return carAlbum?.imageResId ?: R.drawable.defaults
}

fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}