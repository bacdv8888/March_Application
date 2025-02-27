package com.example.marchapplication.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
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
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun saveImageToGallery(context: Context, bitmap: Bitmap): Uri? {
    val filename = "IMG_${System.currentTimeMillis()}.jpg"
    var imageUri: Uri? = null

    try {
        val outputStream: OutputStream?

        // Android 10+ uses MediaStore to save photos
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        imageUri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
        )

        outputStream = imageUri?.let { context.contentResolver.openOutputStream(it) }

        outputStream?.use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            Toast.makeText(context, "Photo saved to Album!", Toast.LENGTH_SHORT).show()
        }

    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error saving image!", Toast.LENGTH_SHORT).show()
    }

    return imageUri
}
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
        CarAlbum("CX-90", R.drawable.a),
        CarAlbum("CX-80", R.drawable.b),
        CarAlbum("CX-8", R.drawable.c),
        CarAlbum("CX-70", R.drawable.d),
        CarAlbum("CX-60", R.drawable.e),
        CarAlbum("CX-50", R.drawable.f),
        CarAlbum("CX-5", R.drawable.g),
        CarAlbum("CX-30", R.drawable.h),
        CarAlbum("CX-3", R.drawable.i),
        CarAlbum("BT-50", R.drawable.k),
        CarAlbum("MX-5", R.drawable.l),
        CarAlbum("MX-30", R.drawable.m)
    )
    val carAlbum = carAlbumList.find { it.carName == folderName }
    return carAlbum?.imageResId ?: R.drawable.defaults
}

fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}